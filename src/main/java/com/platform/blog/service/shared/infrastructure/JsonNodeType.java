package com.platform.blog.service.shared.infrastructure;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.sql.Types;

public class JsonNodeType extends AbstractJavaType<JsonNode> {

    public static final JsonNodeType INSTANCE = new JsonNodeType();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public JsonNodeType() {
        super(JsonNode.class);
    }

    @Override
    public JdbcType getRecommendedJdbcType(JdbcTypeIndicators indicators) {
        return indicators.getJdbcType(Types.OTHER);
    }

    @Override
    public JsonNode fromString(CharSequence string) {
        try {
            return MAPPER.readTree(string.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize JSON: " + string, e);
        }
    }

    @Override
    public <X> X unwrap(JsonNode value, Class<X> type, WrapperOptions options) {
        if (value == null) return null;
        if (String.class.isAssignableFrom(type)) {
            try {
                return type.cast(MAPPER.writeValueAsString(value));
            } catch (Exception e) {
                throw new IllegalArgumentException("Could not serialize JsonNode", e);
            }
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> JsonNode wrap(X value, WrapperOptions options) {
        if (value == null) return null;
        try {
            return MAPPER.readTree(value.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize to JsonNode: " + value, e);
        }
    }
}

