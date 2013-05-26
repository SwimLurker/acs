package org.slstudio.acs.tr069.instruction.extension.func;

import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.util.InstructionUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ÉÏÎç4:08
 */
public class ProductFunction implements IFunction , Cloneable{
    private String arg1 = null;
    private String arg2 = null;

    public void setArgs(String[] args) throws InstructionException {
        if(args.length != 2){
            throw new InstructionException("Invaid arguments number for multiplication function");
        }
        this.arg1 = args[0].trim();
        this.arg2 = args[1].trim();
    }

    public String execute(InstructionContext context) throws InstructionException {
        try{
            String s1 = InstructionUtil.populateTextWithVariableValue(arg1, context.getSymbolTable());
            String s2 = InstructionUtil.populateTextWithVariableValue(arg2, context.getSymbolTable());
            int i1 = Integer.parseInt(s1);
            int i2 = Integer.parseInt(s2);
            return Integer.toString(i1 * i2);

        }catch (Exception exp){
            throw new InstructionException("Execute multiplication function failed", exp);
        }
    }

    public String getName() {
        return "PRODUCT";
    }

    public IFunction newInstance() {
        return new ProductFunction();
    }

    public String toString(){
        return arg1 + "*" + arg2;
    }

}
