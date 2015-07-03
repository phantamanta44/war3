package io.github.phantamanta44.war3.model.obj;

import io.github.phantamanta44.war3.model.IModelCustom;
import io.github.phantamanta44.war3.model.IModelCustomLoader;
import io.github.phantamanta44.war3.model.ModelFormatException;
import net.minecraft.util.ResourceLocation;

public class ObjModelLoader implements IModelCustomLoader
{

    @Override
    public String getType()
    {
        return "OBJ model";
    }

    private static final String[] types = { "obj" };
    @Override
    public String[] getSuffixes()
    {
        return types;
    }

    @Override
    public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
    {
        return new WavefrontObject(resource);
    }
}