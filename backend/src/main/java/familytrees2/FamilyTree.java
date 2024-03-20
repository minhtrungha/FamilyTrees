package familytrees2;
import java.util.function.Predicate;

public class FamilyTree {
    private FamilyTreeNode root;

    public FamilyTree(Person rootPerson) {
        this.root = new FamilyTreeNode(rootPerson);
    }

    public FamilyTree(FamilyTreeNode rootPerson) {
        this.root = rootPerson;
    }

    public FamilyTreeNode getRoot() {
        return root;
    }

    public FamilyTreeNode findPerson(Predicate<Person> condition) {
        if (root != null) {
            return root.findPerson(condition);
        }
        return null;
    }
}
