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
 * cosh(a);
 * Returns the hyperbolic cosine of a (a is in radians).
 * Accept Type(s) :DOUBLE/INT/FLOAT/LONG
 * Return Type(s): DOUBLE
 */
@Extension(
        name = "cosh",
        namespace = "math",
        description = "This function returns the hyperbolic cosine of `p1` which is in radians. It wraps " +
                "the `java.lang.Math.cosh()` function.",
        parameters = {
                @Parameter(
                        name = "p1",
                        description = "The value of the parameter whose hyperbolic cosine should be found. " +
                                "The input is required to be in radians.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE})},
        returnAttributes = @ReturnAttribute(
                description = "The hyperbolic cosine value of the input parameter.",
                type = {DataType.DOUBLE}),
        examples = @Example(

                syntax = "define stream InValueStream (inValue double); \n" +
                        "from InValueStream \n" +
                        "select math:cosh(inValue) as cosValue \n" +
                        "insert into OutMediationStream;",
                description = "If the 'inValue' is given, the function calculates the hyperbolic cosine value for" +
                       " the same and directs the output to the output stream, OutMediationStream. For example, " +
                        "cosh (6d) returns 201.7156361224559.")
)
public class CoshFunctionExtension extends FunctionExecutor {
    @Override
    protected StateFactory init(ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to math:cosh() function, " +
                    "required 1, but found " + attributeExpressionExecutors.length);
        }
        Attribute.Type attributeType = attributeExpressionExecutors[0].getReturnType();
        if (!((attributeType == Attribute.Type.DOUBLE)
                || (attributeType == Attribute.Type.INT)
                || (attributeType == Attribute.Type.FLOAT)
                || (attributeType == Attribute.Type.LONG))) {
            throw new SiddhiAppValidationException("Invalid parameter type found for the argument of math:cosh() " +
                    "function, required " + Attribute.Type.INT + " or " + Attribute.Type.LONG +
                    " or " + Attribute.Type.FLOAT + " or " + Attribute.Type.DOUBLE +
                    ", but found " + attributeType.toString());
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        return null;    // This method won't get called. Hence, unimplemented.
    }

    @Override
    protected Object execute(Object data, State state) {
        if (data != null) {
            //type-conversion
            if (data instanceof Integer) {
                int inputInt = (Integer) data;
                return Math.cosh((double) inputInt);
            } else if (data instanceof Long) {
                long inputLong = (Long) data;
                return Math.cosh((double) inputLong);
            } else if (data instanceof Float) {
                float inputFloat = (Float) data;
                return Math.cosh((double) inputFloat);
            } else if (data instanceof Double) {
                return Math.cosh((Double) data);
            }
        } else {
            throw new SiddhiAppRuntimeException("Input to the math:cosh() function cannot be null");
        }
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.DOUBLE;
    }

}
