package mat06.sim.missingitemreminder.models;

public class  AdapterWrapper {
    private int type;
    private MissingItem missingItem;

    public AdapterWrapper(int type, MissingItem missingItem) {
        this.type = type;
        this.missingItem = missingItem;
    }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public MissingItem getMissingItem() { return missingItem; }

    public void setMissingItem(MissingItem missingItem) { this.missingItem = missingItem; }
}
