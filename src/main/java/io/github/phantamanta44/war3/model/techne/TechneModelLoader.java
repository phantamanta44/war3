package io.github.phantamanta44.war3.model.techne;

import io.github.phantamanta44.war3.model.IModelCustom;
import io.github.phantamanta44.war3.model.IModelCustomLoader;
import io.github.phantamanta44.war3.model.ModelFormatException;
import net.minecraft.util.ResourceLocation;

public class TechneModelLoader implements IModelCustomLoader {
    
    @Override
    public String getType()
    {
        return "Techne model";
    }

    private static final String[] types = { "tcn" };
    @Override
    public String[] getSuffixes()
    {
        return types;
    }

    @Override
    public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
    {
        return new TechneModel(resource);
    }

}