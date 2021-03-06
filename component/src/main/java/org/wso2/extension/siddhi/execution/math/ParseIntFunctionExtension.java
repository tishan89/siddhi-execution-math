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
 * parseInt(string);
 * Returns the 'string' as an INTEGER
 * Accept Type(s): STRING
 * Return Type(s): INT
 */
@Extension(
        name = "parseInt",
        namespace = "math",
        description = "This function returns the integer value of the received string.",
        parameters = {
                @Parameter(
                        name = "p1",
                        description = "The value that should be converted to an integer.",
                        type = {DataType.STRING})},
        returnAttributes = @ReturnAttribute(
                description = "The integer value of the input parameter.",
                type = {DataType.INT}),
        examples = @Example(

                syntax = "define stream InValueStream (inValue string); \n" +
                        "from InValueStream \n" +
                        "select math:parseInt(inValue) as output \n" +
                        "insert into OutMediationStream;",
                description = "The  function converts the 'inValue' into its corresponding " +
                        "integer value and directs the output to the output stream, OutMediationStream. " +
                        "For example, parseInt(\"123\") returns 123.")
)
public class ParseIntFunctionExtension extends FunctionExecutor {
    @Override
    protected StateFactory init(ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to math:parseInt() function, " +
                    "required 1, but found " + attributeExpressionExecutors.length);
        }
        if (attributeExpressionExecutors[0].getReturnType() != Attribute.Type.STRING) {
            throw new SiddhiAppValidationException("Invalid parameter type found for the argument of " +
                    "math:parseInt() function, required " + Attribute.Type.STRING +
                    " but found " + attributeExpressionExecutors[0].getReturnType().toString());
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
            return Integer.parseInt((String) data);
        } else {
            throw new SiddhiAppRuntimeException("Input to the math:parseInt() function cannot be null");
        }
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.INT;
    }


}
