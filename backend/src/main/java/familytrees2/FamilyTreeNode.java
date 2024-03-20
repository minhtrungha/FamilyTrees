package familytrees2;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class FamilyTreeNode {
    private Person person;
    private List<FamilyTreeNode> littles;

    public FamilyTreeNode(Person person) {
        this.person = person;
        this.littles = new ArrayList<>();
    }

    public FamilyTreeNode addLittle(Person little) {
        FamilyTreeNode childNode = new FamilyTreeNode(little);
        this.littles.add(childNode);
        return childNode;
    }

    public void addLittle(FamilyTreeNode littleNode) {
        this.littles.add(littleNode);
    }

    public FamilyTreeNode findPerson(Predicate<Person> condition) {
        if (condition.test(person)) {
            return this;
        }
        for (FamilyTreeNode child : littles) {
            FamilyTreeNode result = child.findPerson(condition);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public Person getPerson() {
        return person;
    }

    public List<FamilyTreeNode> getLittles() {
        return new ArrayList<>(littles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyTreeNode that = (FamilyTreeNode) o;
        return Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person);
    }
}
