/* Generated By:JJTree: Do not edit this line. ASTMethod.java */

package org.apache.velocity.runtime.parser.node;

import java.lang.reflect.Method;

import org.apache.velocity.Context;
import org.apache.velocity.runtime.parser.*;
import org.apache.velocity.util.introspection.Introspector;

public class ASTMethod extends SimpleNode
{
    private String methodName;
    private int paramCount;
    private Method method;
    private Object[] params;

    public ASTMethod(int id)
    {
        super(id);
    }

    public ASTMethod(Parser p, int id)
    {
        super(p, id);
    }

    /** Accept the visitor. **/
    public Object jjtAccept(ParserVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    public Object init(Context context, Object data)
        throws Exception
    {
        methodName = getFirstToken().image;
        
        paramCount = jjtGetNumChildren() - 1;
        params = new Object[paramCount];
        
        // Now the parameters have to be processed, there
        // may be references contained within that need
        // to be introspected.
        
        for (int i = 0; i < paramCount; i++)
            jjtGetChild(i + 1).init(context, null);

        for (int j = 0; j < paramCount; j++)
            params[j] = jjtGetChild(j + 1).value(context);

        method = Introspector.getMethod((Class) data, methodName, params);
        
        if (method == null)
            return null;
        
        return method.getReturnType();
    }
    
    public Object execute(Object o, Context context)
    {
        // I need to pass in the arguments to the
        // method. 

        for (int j = 0; j < paramCount; j++)
            params[j] = jjtGetChild(j + 1).value(context);
        
        try
        {
            return method.invoke(o, params);
        }
        catch (Exception e)
        {
            return null;
        }            
    }
}
