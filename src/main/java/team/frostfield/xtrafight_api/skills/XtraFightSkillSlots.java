package team.frostfield.xtrafight_api.skills;

import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum XtraFightSkillSlots implements SkillSlot {
    HEAVY_ATTACK(XtraFightAPISkillCategories.HEAVY_ATTACK),
    COUNTER_ATTACK(XtraFightAPISkillCategories.COUNTER_ATTACK);

    final int id;
    final SkillCategory category;

    XtraFightSkillSlots(SkillCategory category) {
        this.id = SkillSlot.ENUM_MANAGER.assign(this);
        this.category = category;

    }
    @Override
    public SkillCategory category() {
        return this.category;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
