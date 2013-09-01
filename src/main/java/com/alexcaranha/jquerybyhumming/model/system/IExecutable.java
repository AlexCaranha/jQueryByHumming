package com.alexcaranha.jquerybyhumming.model.system;

import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public interface IExecutable {
    public void execute(Map<String, Object> params) throws Exception;
}
