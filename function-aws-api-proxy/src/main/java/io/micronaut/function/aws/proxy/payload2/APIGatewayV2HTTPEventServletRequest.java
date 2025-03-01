/*
 * Copyright 2017-2023 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.function.aws.proxy.payload2;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.util.StringUtils;
import io.micronaut.function.aws.proxy.ApiGatewayServletRequest;
import io.micronaut.function.aws.proxy.MapCollapseUtils;
import io.micronaut.function.aws.proxy.MapListOfStringAndMapStringMutableHttpParameters;
import io.micronaut.http.CaseInsensitiveMutableHttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpHeaders;
import io.micronaut.http.MutableHttpParameters;
import io.micronaut.http.codec.MediaTypeCodecRegistry;
import io.micronaut.servlet.http.BodyBuilder;
import io.micronaut.servlet.http.ServletHttpRequest;
import io.micronaut.servlet.http.ServletHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link ServletHttpRequest} for AWS API Gateway Proxy.
 *
 * @param <B> The body type
 * @author Tim Yates
 * @since 4.0.0
 */
@Internal
public final class APIGatewayV2HTTPEventServletRequest<B> extends ApiGatewayServletRequest<B, APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private static final Logger LOG = LoggerFactory.getLogger(APIGatewayV2HTTPEventServletRequest.class);

    private final APIGatewayV2HTTPResponseServletResponse<Object> response;

    public APIGatewayV2HTTPEventServletRequest(
        APIGatewayV2HTTPEvent requestEvent,
        APIGatewayV2HTTPResponseServletResponse<Object> response,
        MediaTypeCodecRegistry codecRegistry,
        ConversionService conversionService,
        BodyBuilder bodyBuilder
    ) {
        super(
            conversionService,
            codecRegistry,
            requestEvent,
            URI.create(requestEvent.getRequestContext().getHttp().getPath()),
            parseMethod(requestEvent),
            LOG,
            bodyBuilder
        );
        this.response = response;
    }

    private static HttpMethod parseMethod(APIGatewayV2HTTPEvent requestEvent) {
        try {
            return HttpMethod.valueOf(requestEvent.getRequestContext().getHttp().getMethod());
        } catch (IllegalArgumentException e) {
            return HttpMethod.CUSTOM;
        }
    }

    @Override
    public byte[] getBodyBytes() throws IOException {
        String body = requestEvent.getBody();
        if (StringUtils.isEmpty(body)) {
            throw new IOException("Empty Body");
        }
        return requestEvent.getIsBase64Encoded() ?
            Base64.getDecoder().decode(body) : body.getBytes(getCharacterEncoding());
    }

    @Override
    public MutableHttpHeaders getHeaders() {
        return new CaseInsensitiveMutableHttpHeaders(MapCollapseUtils.collapse(Collections.emptyMap(), requestEvent.getHeaders()), conversionService);
    }

    @NonNull
    private static List<String> splitCommaSeparatedValue(@Nullable String value) {
        if (value == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(value.split(","));
    }

    @NonNull
    private static Map<String, List<String>> transformCommaSeparatedValue(@Nullable Map<String, String> input) {
        if (input == null) {
            return Collections.emptyMap();
        }
        Map<String, List<String>> output = new HashMap<>();
        for (var entry: input.entrySet()) {
            output.put(entry.getKey(), splitCommaSeparatedValue(entry.getValue()));
        }
        return output;
    }

    @Override
    public MutableHttpParameters getParameters() {
        MediaType mediaType = getContentType().orElse(MediaType.APPLICATION_JSON_TYPE);
        if (isFormSubmission(mediaType)) {
            return getParametersFromBody(requestEvent.getQueryStringParameters());
        } else {
            return new MapListOfStringAndMapStringMutableHttpParameters(conversionService, transformCommaSeparatedValue(requestEvent.getQueryStringParameters()), Collections.emptyMap());
        }
    }

    @Override
    public ServletHttpResponse<APIGatewayV2HTTPResponse, ?> getResponse() {
        return response;
    }
}
