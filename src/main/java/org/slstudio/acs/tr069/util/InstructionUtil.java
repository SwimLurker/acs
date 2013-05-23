package org.slstudio.acs.tr069.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.instruction.InstructionConstants;
import org.slstudio.acs.util.Tuple;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-22
 * Time: ÏÂÎç9:30
 */
public class InstructionUtil {
    public static String populateTextWithVariableValue(String sourceText, Map<String, Object> symbolTable) throws InstructionException{
        Set<String> keys = symbolTable.keySet();
        boolean bEndLoop = false;
        String populateVariableName = null;
        String text = sourceText;

        while(!bEndLoop){
            boolean bNeedPopulate = false;
            for(String name : keys){
                if(checkNeedPopulateVariable(text, name)){
                    populateVariableName = name;
                    bNeedPopulate = true;
                    break;
                }
            }
            if(bNeedPopulate){
                text = populateVariableValue(text, populateVariableName, symbolTable.get(populateVariableName));
                keys.remove(populateVariableName);
                if(keys.size() == 0){
                    bEndLoop = true;
                }
            }else{
                bEndLoop = true;
            }
        }
        return text;
    }

    private static boolean checkNeedPopulateVariable(String text, String variableName){
        return text.contains(variableName);
    }

    private static String populateVariableValue(String text, String variableName, Object variableValue)  throws InstructionException {
        String valueStr = "";
        String variableExpression = null;
        String resultText = text;
        while((variableExpression = getVariableExpression(resultText, variableName)) != null){
            valueStr = evaluateVariableExpressionValue(variableExpression, variableValue);
            resultText = resultText.replace(variableExpression, (valueStr==null?"":valueStr));
        }
        return resultText;
    }

    //get the variable expression which is like "{$A.B}"
    private static String getVariableExpression(String messageText, String variableName) {
        int pos = messageText.indexOf(InstructionConstants.SYMBOLNAME_VARIABLEREF_PREFIX+variableName);
        if(pos != -1){
            int lastPos = messageText.indexOf(InstructionConstants.SYMBOLNAME_VARIABLEREF_POSTFIX, pos);
            if(lastPos!=-1){
                return messageText.substring(pos, lastPos+1);
            }
        }
        return null;
    }

    //evaluate the variable expression (ex: {$A.B}) value by repeat get A's value and then get A's B property value
    private static String evaluateVariableExpressionValue(String variableExpression, Object variableValue) throws InstructionException{
        //first get variable expression without {}
        String variableExpressionText = variableExpression.substring(1, variableExpression.length()-1);

        if(variableValue instanceof String){
            String valueStr = (String)variableValue;
            if(valueStr.toUpperCase().startsWith("JSON:")){
                String jsonValueStr = valueStr.substring(5);
                //should be json string
                JsonNode jsonObjValue = getVariableValueAsJsonOjbect(jsonValueStr);

                String propertyText = getPropertyFullText(variableExpressionText);
                while((propertyText != null)&&(!propertyText.equals(""))){
                    jsonObjValue = getVariablePropertyValue(jsonObjValue ,propertyText);
                    propertyText = getPropertyFullText(propertyText);
                }
                return jsonObjValue == null?null:jsonObjValue.toString();
            }else{
                //normal value just check if the variable has property
                if(getPropertyFullText(variableExpressionText) == null){
                    return valueStr;
                }else{
                    throw new InstructionException("can not evaluate the variable's property");
                }
            }
        }else{
            throw new InstructionException("unknown variable value type, should be string(json string for object)");
        }
    }

    // check if the value is another variable reference
    private static boolean isVariableRef(String valueStr) {
        return valueStr.startsWith(InstructionConstants.SYMBOLNAME_VARIABLE_PREFIX);
    }

    private static JsonNode getVariableValueAsJsonOjbect(String variableValueString) {
        MappingJsonFactory f = new MappingJsonFactory();
        try {
            JsonParser parser = f.createJsonParser(variableValueString);
            return parser.readValueAsTree();
        } catch (Exception e) {
            return null;
        }
    }

    //get variableExpressionText $A.B.C 's property text B.C
    private static String getPropertyFullText(String variableExpressionText) {
        int pos = variableExpressionText.indexOf(InstructionConstants.SYMBOLNAME_ACCESSOPER);
        if(pos != -1){
            return variableExpressionText.substring(pos+1);
        }
        return null;
    }

    //get variable 's property value
    private static JsonNode getVariablePropertyValue(JsonNode jsonValue, String propertyText) throws InstructionException {

        String propertyName = getPropertyName(propertyText);

        if(propertyName == null){
            return jsonValue;
        }else if(jsonValue.isObject()){
            //check if property is array
            Tuple.Tuple2<String, Integer> result = getArrayPropertyIndex(propertyName);
            if((result != null)&&(result._2().intValue()>=0)){
                return jsonValue.get(result._1()).get(result._2().intValue());
            }else{
                //not array, just normal property
                return jsonValue.get(propertyName);
            }
        }
        return null;
    }

    private static String getPropertyName(String propertyText) {
        int pos = propertyText.indexOf(InstructionConstants.SYMBOLNAME_ACCESSOPER);
        if(pos != -1){
            return propertyText.substring(0,pos);
        }else{
            return propertyText;
        }
    }

    //get array property name and index
    //return null is property is not array property
    private static Tuple.Tuple2<String, Integer> getArrayPropertyIndex(String propertyName) throws InstructionException{
        int resultIndex = -1;
        int pos =  propertyName.indexOf(InstructionConstants.SYMBOLNAME_ARRAYREF_PREFIX);
        if(pos != -1){
            String arrayPropertyName = propertyName.substring(0, pos);
            int endPos = propertyName.indexOf(InstructionConstants.SYMBOLNAME_ARRAYREF_POSTFIX, pos);
            if(endPos != -1){
                String indexStr = propertyName.substring(pos+1,endPos);
                try{
                    resultIndex = Integer.parseInt(indexStr);
                    return Tuple.Tuple2.tuple(arrayPropertyName, new Integer(resultIndex));
                }catch (Exception exp){
                    throw new InstructionException("Incorrect array index for array type property");
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        JsonNode obj = InstructionUtil.getVariableValueAsJsonOjbect("\"abc\"");
        System.out.println(obj);
    }

}
