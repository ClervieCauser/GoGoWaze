package com.example.gogowaze.model;

import java.util.Map;

public abstract class VilleSimpleFactory implements VilleInterface {
    public static VilleInterface createVille(String villeName, Map<String, Integer> gravite, Map<String, Integer> typeAccident) {
        if(villeName == null || villeName.isEmpty()) {
            throw new IllegalArgumentException("villeName cannot be null or empty");
        }

        if(gravite == null) {
            throw new IllegalArgumentException("gravite cannot be null");
        }

        if(typeAccident == null) {
            throw new IllegalArgumentException("typeAccident cannot be null");
        }

        // Ici, vous pouvez ajouter des vérifications pour vous assurer que les Maps gravite et typeAccident respectent certaines règles
        // Par exemple, vous pouvez vérifier que les Maps ne sont pas vides, qu'elles contiennent des types spécifiques, etc.

        return new VilleImpl(villeName, gravite, typeAccident);
    }
}
