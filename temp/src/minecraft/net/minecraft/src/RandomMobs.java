// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            Entity, EntityLiving, EntityPlayer, World, 
//            Config, RenderEngine, TexturePackList, TexturePackBase

public class RandomMobs
{

    private static Map textureVariantsMap = new HashMap();
    private static Random random = new Random();

    public RandomMobs()
    {
    }

    public static void entityLoaded(Entity entity)
    {
        if(entity.field_20047_bv != null)
        {
            return;
        }
        if(!(entity instanceof EntityLiving))
        {
            return;
        }
        if(entity instanceof EntityPlayer)
        {
            return;
        } else
        {
            EntityLiving entityliving = (EntityLiving)entity;
            int i = entityliving.persistentId;
            entity.field_20047_bv = (new StringBuilder()).append("").append(i).toString();
            return;
        }
    }

    public static void worldChanged(World world, World world1)
    {
        if(world1 != null)
        {
            List list = world1.func_658_i();
            for(int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);
                entityLoaded(entity);
            }

        }
    }

    public static int getTexture(String s, String s1)
    {
        if(s1 == null)
        {
            return -1;
        }
        if(s == null)
        {
            return -1;
        }
        if(s.length() <= 1)
        {
            return -1;
        }
        char c = s.charAt(0);
        if(c < '0' || c > '9')
        {
            return -1;
        }
        int i = Math.abs(s.hashCode());
        String as[] = (String[])textureVariantsMap.get(s1);
        if(as == null)
        {
            as = getTextureVariants(s1);
            textureVariantsMap.put(s1, as);
        }
        if(as == null || as.length <= 0)
        {
            return -1;
        } else
        {
            int j = i % as.length;
            String s2 = as[j];
            return Config.getMinecraft().field_6315_n.func_1070_a(s2);
        }
    }

    private static String[] getTextureVariants(String s)
    {
        RenderEngine renderengine = Config.getMinecraft().field_6315_n;
        String as[] = {
            s
        };
        int i = s.lastIndexOf('.');
        if(i < 0)
        {
            return as;
        }
        String s1 = s.substring(0, i);
        String s2 = s.substring(i);
        int j = getCountTextureVariants(s, s1, s2);
        if(j <= 1)
        {
            return as;
        }
        as = new String[j];
        as[0] = s;
        for(int k = 1; k < as.length; k++)
        {
            int l = k + 1;
            as[k] = (new StringBuilder()).append(s1).append(l).append(s2).toString();
        }

        Config.dbg((new StringBuilder()).append("RandomMobs: ").append(s).append(", variants: ").append(as.length).toString());
        return as;
    }

    private static int getCountTextureVariants(String s, String s1, String s2)
    {
        RenderEngine renderengine = Config.getMinecraft().field_6315_n;
        char c = '\u03E8';
        int i = 2;
label0:
        do
        {
label1:
            {
                if(i >= c)
                {
                    break label0;
                }
                String s3 = (new StringBuilder()).append(s1).append(i).append(s2).toString();
                try
                {
                    InputStream inputstream = renderengine.field_6527_k.field_6534_a.func_6481_a(s3);
                    if(inputstream != null)
                    {
                        inputstream.close();
                        break label1;
                    }
                }
                catch(IOException ioexception) { }
                return i - 1;
            }
            i++;
        } while(true);
        return c;
    }

    public static void resetTextures()
    {
        textureVariantsMap.clear();
    }

}
