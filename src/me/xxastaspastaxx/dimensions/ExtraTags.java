package me.xxastaspastaxx.dimensions;

import org.bukkit.Material;

/**
 * Created on 10/3/2018.
 *
 * @author RoboMWM
 */
public class ExtraTags
{
    private ExtraTags(){}

    public static boolean isSTAINED_GLASS_PANE(Material material)
    {
        switch (material)
        {
            case GLASS_PANE:
                return true;
        }
        return material.name().contains("STAINED_GLASS_PANE");
    }
}
