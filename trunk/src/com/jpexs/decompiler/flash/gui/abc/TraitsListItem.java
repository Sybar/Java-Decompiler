package com.jpexs.decompiler.flash.gui.abc;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.types.traits.Trait;
import com.jpexs.decompiler.flash.abc.types.traits.TraitMethodGetterSetter;
import com.jpexs.decompiler.flash.abc.types.traits.TraitSlotConst;
import com.jpexs.decompiler.flash.gui.AppStrings;
import com.jpexs.decompiler.flash.tags.ABCContainerTag;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JPEXS
 */
public class TraitsListItem {

    private Type type;
    private boolean isStatic;
    private List<ABCContainerTag> abcTags;
    private ABC abc;
    private int classIndex;
    private int index;
    private int scriptIndex;
    public String STR_INSTANCE_INITIALIZER = AppStrings.translate("abc.traitslist.instanceinitializer");
    public String STR_CLASS_INITIALIZER = AppStrings.translate("abc.traitslist.classinitializer");

    public TraitsListItem(Type type, int index, boolean isStatic, List<ABCContainerTag> abcTags, ABC abc, int classIndex, int scriptIndex) {
        this.type = type;
        this.index = index;
        this.isStatic = isStatic;
        this.abcTags = abcTags;
        this.abc = abc;
        this.classIndex = classIndex;
        this.scriptIndex = scriptIndex;
    }

    public int getGlobalTraitId() {
        if (type == Type.INITIALIZER) {
            if (!isStatic) {
                return abc.class_info[classIndex].static_traits.traits.length + abc.instance_info[classIndex].instance_traits.traits.length;
            } else {
                return abc.class_info[classIndex].static_traits.traits.length + abc.instance_info[classIndex].instance_traits.traits.length + 1;
            }
        }
        if (isStatic) {
            return index;
        } else {
            return abc.class_info[classIndex].static_traits.traits.length + index;
        }
    }

    public String toStringName() {
        if ((type != Type.INITIALIZER) && isStatic) {
            return abc.class_info[classIndex].static_traits.traits[index].getName(abc).getName(abc.constants, new ArrayList<String>());
        } else if ((type != Type.INITIALIZER) && (!isStatic)) {
            return abc.instance_info[classIndex].instance_traits.traits[index].getName(abc).getName(abc.constants, new ArrayList<String>());
        } else if (!isStatic) {
            return "__" + STR_INSTANCE_INITIALIZER;
        } else {
            return "__" + STR_CLASS_INITIALIZER;
        }
    }

    @Override
    public String toString() {
        if ((type != Type.INITIALIZER) && isStatic) {
            return abc.class_info[classIndex].static_traits.traits[index].convertHeader("", abcTags, abc, true, false, scriptIndex, classIndex, false, new ArrayList<String>(), false);
        } else if ((type != Type.INITIALIZER) && (!isStatic)) {
            return abc.instance_info[classIndex].instance_traits.traits[index].convertHeader("", abcTags, abc, false, false, scriptIndex, classIndex, false, new ArrayList<String>(), false);
        } else if (!isStatic) {
            return STR_INSTANCE_INITIALIZER;
        } else {
            return STR_CLASS_INITIALIZER;
        }
    }

    public Type getType() {
        return type;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public enum Type {

        METHOD,
        VAR,
        CONST,
        INITIALIZER;

        public static Type getTypeForTrait(Trait t) {
            if (t instanceof TraitMethodGetterSetter) {
                return METHOD;
            }
            if (t instanceof TraitSlotConst) {
                if (((TraitSlotConst) t).isConst()) {
                    return CONST;
                } else {
                    return VAR;
                }
            }
            return null;
        }
    }
}
