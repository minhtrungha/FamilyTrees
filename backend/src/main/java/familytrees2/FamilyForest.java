package familytrees2;
import java.util.ArrayList;
import java.util.function.Predicate;

public class FamilyForest {
    private ArrayList<FamilyTree> familyForest;

    public FamilyForest() {
        familyForest = new ArrayList<>();
    }

    public void addFamilyTree(FamilyTree familyTree) {
        if (familyTree != null) {
            familyForest.add(familyTree);
        } else {
            System.out.println("Cannot add a null family tree.");
        }
    }

    public FamilyTreeNode findPersonInForest(Predicate<Person> condition) {
        for (FamilyTree familyTree : familyForest) {
            FamilyTreeNode result = familyTree.getRoot().findPerson(condition);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

  public void removeFamilyTree(FamilyTree tree) {
      familyForest.remove(tree);
  }

    public ArrayList<FamilyTree> getFamilyForest() {
        return new ArrayList<>(familyForest);
    }
}
