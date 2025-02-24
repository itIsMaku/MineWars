package cz.maku.gameapi.configuration;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GameConfigurationExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        return field.getAnnotation(Exclude.class) != null;
    }
}
