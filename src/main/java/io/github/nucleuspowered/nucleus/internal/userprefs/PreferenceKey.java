/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.userprefs;

import io.github.nucleuspowered.nucleus.Nucleus;
import org.spongepowered.api.entity.living.player.User;

import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;

public class PreferenceKey<T> implements io.github.nucleuspowered.nucleus.api.service.NucleusUserPreferenceService.PreferenceKey<T> {

    private final String key;
    @Nullable private final T def;
    private final Class<T> clazz;
    private final Predicate<User> canAccess;
    private final String descriptionKey;

    PreferenceKey(String key, @Nullable T def, Class<T> clazz, String permission, String descriptionKey) {
        this(key, def, clazz, user -> Nucleus.getNucleus().getPermissionResolver().hasPermission(user, permission), descriptionKey);
    }

    PreferenceKey(String key, @Nullable T def, Class<T> clazz, Predicate<User> canAccess, String descriptionKey) {
        this.key = key;
        this.def = def;
        this.clazz = clazz;
        this.canAccess = canAccess;
        this.descriptionKey = descriptionKey;
    }

    public String getID() {
        return this.key;
    }

    public Optional<T> getDefaultValue() {
        return Optional.ofNullable(this.def);
    }

    public Class<T> getValueClass() {
        return this.clazz;
    }

    @Override
    public boolean canAccess(User user) {
        return this.canAccess.test(user);
    }

    public String getDescription() {
        return Nucleus.getNucleus().getMessageProvider().getMessageWithFormat(this.descriptionKey);
    }

    public String getDescriptionKey() {
        return this.descriptionKey;
    }

    public static class BooleanKey extends PreferenceKey<Boolean> {

        public BooleanKey(String key, @Nullable Boolean def, String permission, String descriptionKey) {
            super(key, def, Boolean.class, permission, descriptionKey);
        }

        public BooleanKey(String key, @Nullable Boolean def, Predicate<User> canAccess, String descriptionKey) {
            super(key, def, Boolean.class, canAccess, descriptionKey);
        }
    }
}
