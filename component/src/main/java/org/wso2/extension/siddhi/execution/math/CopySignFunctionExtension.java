/*
 * Copyright (c)  2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.extension.siddhi.execution.math;

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.exception.SiddhiAppRuntimeException;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.executor.function.FunctionExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;
import io.siddhi.query.api.exception.SiddhiAppValidationException;

/**
 * copysign(magnitude,sign);
 * Returns the first argument with the sign of the second argument.
 * magnitude - Accept Type(s):DOUBLE/INT/FLOAT/LONG
 * sign - Accept Type(s):DOUBLE/INT/FLOAT/LONG
 * Return Type(s): DOUBLE
 */
@Extension(
        name = "copySign",
        namespace = "math",
        description = "This function returns a value of an input with the received `magnitude` and `sign` " +
                "of another input. It wraps the `java.lang.Math.copySign()` function.",
        parameters = {
                @Parameter(
                        name = "magnitude",
                        description = "The magnitude of this parameter is used in the output attribute.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE}),
                @Parameter(
                        name = "sign",
                        description = "The sign of this parameter is used in the output attribute.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE}),
        },
        returnAttributes = @ReturnAttribute(
                description = "This returns the first argument with the sign of the second argument.",
                type = {DataType.DOUBLE}),
        examples = @Example(

                syntax = "define stream InValueStream (inValue1 double, inValue2 double); \n" +
                        "from InValueStream \n" +
                        "select math:copySign(inValue1,inValue2) as copysignValue \n" +
                        "insert into OutMediationStream;",
                description = "If two values are provided as 'inValue1' and 'inValue2', the function copies " +
                               "the magnitude and sign of the second argument into the first one and directs " +
                               "the result to the output stream, OutMediatonStream. For example, " +
                               "copySign(5.6d, -3.0d) returns -5.6.")
)
public class CopySignFunctionExtension extends FunctionExecutor {
    @Override
    protected StateFactory init(ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length != 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to math:copysign() function, " +
                    "required 2, but found " + attributeExpressionExecutors.length);
        }
        Attribute.Type attributeType = attributeExpressionExecutors[0].getReturnType();
        if (!((attributeType == Attribute.Type.DOUBLE)
                || (attributeType == Attribute.Type.INT)
                || (attributeType == Attribute.Type.FLOAT)
                || (attributeType == Attribute.Type.LONG))) {
            throw new SiddhiAppValidationException("Invalid parameter type found for the first argument of " +
                    "math:copysign() function, required " + Attribute.Type.INT + " or " + Attribute.Type.LONG +
                    " or " + Attribute.Type.FLOAT + " or " + Attribute.Type.DOUBLE +
                    ", but found " + attributeType.toString());
        }
        attributeType = attributeExpressionExecutors[1].getReturnType();
        if (!((attributeType == Attribute.Type.DOUBLE)
                || (attributeType == Attribute.Type.INT)
                || (attributeType == Attribute.Type.FLOAT)
                || (attributeType == Attribute.Type.LONG))) {
            throw new SiddhiAppValidationException("Invalid parameter type found for the second argument of " +
                    "math:copysign() function, required " + Attribute.Type.INT + " or " + Attribute.Type.LONG +
                    " or " + Attribute.Type.FLOAT + " or " + Attribute.Type.DOUBLE +
                    ", but found " + attributeType.toString());
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        double inputVal1 = 0d;
        double inputVal2 = 0d;

        if (data[0] != null) {
            //type-conversion
            if (data[0] instanceof Integer) {
                int inputInt = (Integer) data[0];
                inputVal1 = (double) inputInt;
            } else if (data[0] instanceof Long) {
                long inputLong = (Long) data[0];
                inputVal1 = (double) inputLong;
            } else if (data[0] instanceof Float) {
                float inputLong = (Float) data[0];
                inputVal1 = (double) inputLong;
            } else if (data[0] instanceof Double) {
                inputVal1 = (Double) data[0];
            }
        } else {
            throw new SiddhiAppRuntimeException("Input to the math:copysign() function cannot be null");
        }
        if (data[1] != null) {
            //type-conversion
            if (data[1] instanceof Integer) {
                int inputInt = (Integer) data[1];
                inputVal2 = (double) inputInt;
            } else if (data[1] instanceof Long) {
                long inputLong = (Long) data[1];
                inputVal2 = (double) inputLong;
            } else if (data[1] instanceof Float) {
                float inputLong = (Float) data[1];
                inputVal2 = (double) inputLong;
            } else if (data[1] instanceof Double) {
                inputVal2 = (Double) data[1];
            }
        } else {
            throw new SiddhiAppRuntimeException("Input to the math:copysign() function cannot be null");
        }
        return Math.copySign(inputVal1, inputVal2);
    }

    @Override
    protected Object execute(Object data, State state) {
        return null;    // This method won't get called. Hence, unimplemented.
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.DOUBLE;
    }

}
