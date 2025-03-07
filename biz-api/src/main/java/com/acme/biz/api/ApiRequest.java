package com.acme.biz.api;

import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * API 请求对象
 * @author: wuhao
 * @time: 2025/3/6 14:00
 * @since
 * @param <T>
 */
@Deprecated
@Valid
public class ApiRequest <T>  extends ApiBase<T>{

}
