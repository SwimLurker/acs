package org.slstudio.acs.tr069.datamodel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.soap.SOAPUtil;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÉÏÎç11:59
 */
public class TRParameter extends TR069DataModel {

    private static final long serialVersionUID = 645316909304402537L;
    private static final Log log = LogFactory.getLog(TRParameter.class);

    private boolean read;
    private boolean write;
    private TR069Type type;
    private String value;
    private int attributeValue;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = TR069Type.fromString(type);
    }

    public int getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(int attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        if (value != null) {
            this.value = type.getValueByType(value);
        }
    }

    public TRParameter deepClone() {
        TRParameter obj = new TRParameter();
        obj.copyAttribute(this);
        return obj;
    }

    public void copyAttribute(TRParameter obj) {
        this.copyTRDataModelAttribute(obj);
        this.read = obj.read;
        this.write = obj.write;
        this.type = obj.type;
        this.value = obj.value;
        this.attributeValue = obj.attributeValue;
    }

    /**
     * Inner enum type for TR069 data type
     * Encapsulate operation on the "type" and "value" for TR069 parameter
     */
    private enum TR069Type implements Serializable {
        STRING {
            public String getValueByType(Object val) {
                return val.toString();
            }
            @Override
            public String toString() {
                return "string";
            }
        },
        INT {
            public String getValueByType(Object val) {
                String result = val.toString();
                try {
                    Integer.valueOf(result);
                } catch (NumberFormatException e) {
                    log.error("Value and Type are mismatch, Integer Value Expected", e);
                    return "0";
                }
                return result;
            }
            @Override
            public String toString() {
                return "int";
            }
        },
        UNSIGNEDINT {
            public String getValueByType(Object val) {
                String result = val.toString();
                try {
                    Long.valueOf(result);
                } catch (IllegalArgumentException e) {
                    log.error("Value and Type are mismatch, UnsignedInt Value Expected", e);
                    return "0";
                }
                return result;
            }
            @Override
            public String toString() {
                return "unsignedInt";
            }
        },
        BOOLEAN {
            public String getValueByType(Object val) {
                boolean value = false;
                try {
                    value = SOAPUtil.convertToBoolean(val.toString());
                } catch (IllegalArgumentException e) {
                    log.error("Value and Type are mismatch, Boolean Value Expected", e);
                }
                return Boolean.toString(value);
            }
            @Override
            public String toString() {
                return "boolean";
            }
        },
        DATETIME {
            public String getValueByType(Object val) {
                Date date = new Date();
                try {
                    if(val instanceof String)
                        date = Timestamp.valueOf(val.toString().replaceAll("T", " "));
                    else if(val instanceof Calendar)
                        date = ((Calendar)val).getTime();
                    else if(val instanceof Date)
                        date = (Date)val;
                } catch (IllegalArgumentException e) {
                    log.error("Value and Type are mismatch, DateTime Value Expected", e);
                }
                return TR069DataTypeUtil.toISO8601DateTime(date);
            }
            @Override
            public String toString() {
                return "dateTime";
            }
        },
        BASE64 {
            @Override
            public String getValueByType(Object val) {
                String result = "";
                try {
                    if (val instanceof byte[]) {
                        byte[] base64 = (byte[]) val;
                        result = TR069DataTypeUtil.encodeBASE64(base64);
                    } else {
                        byte[] base64 = TR069DataTypeUtil.decodeBASE64(val.toString());
                        result = TR069DataTypeUtil.encodeBASE64(base64);
                    }
                } catch (IOException e) {
                    log.error("Value and Type are mismatch, Base64 Value Expected", e);
                }
                return result;
            }
            @Override
            public String toString() {
                return "base64";
            }
        };

        private static final Map<String, TR069Type> stringToEnum = new HashMap<String, TR069Type>();

        static {
            for (TR069Type type : TR069Type.values()) {
                stringToEnum.put(type.toString().toUpperCase(), type);
            }
        }

        public static TR069Type fromString(String type) {
            return stringToEnum.get(type.toUpperCase());
        }

        public abstract String getValueByType(Object val);
    }

    public void accept(IDataModelVisitor visitor) {
        visitor.visit(this);

    }
    public String toXML() {
        StringBuffer buf = new StringBuffer();
        buf.append("<parameter name=\"");
        buf.append(getName());
        buf.append("\" fullname=\"");
        buf.append(getFullName());
        buf.append("\" read=\"");
        buf.append(isRead());
        buf.append("\" write=\"");
        buf.append(isWrite());
        buf.append("\" type=\"");
        buf.append(getType());
        buf.append("\"/>\n");
        return buf.toString();
    }
}

