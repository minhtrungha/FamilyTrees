package familytrees2;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;

public class FamilyProcessor {
    private FamilyForest familyForest;
    private ObjectMapper objectMapper = new ObjectMapper();

    public FamilyProcessor() {
        familyForest = new FamilyForest();
    }

  public void process(List<String> entries) {
      if (entries.size() < 4 || entries.size() % 4 != 0) {
          return;
      }

      try {
          Person mainPerson = createPersonFromEntries(entries, 0);
          FamilyTreeNode mainPersonNode = findOrNullPersonNode(mainPerson);
          ArrayList <FamilyTreeNode> lilnodes= new ArrayList<>();
        ArrayList <Person> lilpeople= new ArrayList<>();
          for (int i = 4; i < entries.size(); i += 4) {
              Person littlePerson = createPersonFromEntries(entries, i);
            lilpeople.add(littlePerson);
              FamilyTreeNode littleNode = findOrNullPersonNode(littlePerson);
              lilnodes.add(littleNode);
          }
          FamilyTreeNode headPersonFound;
        if(mainPersonNode!=null){
          headPersonFound=mainPersonNode;
        }else{
          headPersonFound=null;
        }
        boolean anyLilsFound=false;
        for(FamilyTreeNode lilnode:lilnodes){
          if(lilnode!=null){
            anyLilsFound=true;
            break;
          }
        }
        if((headPersonFound!=null)&&(!anyLilsFound)){
          for(Person lil:lilpeople){
          headPersonFound.addLittle(new FamilyTreeNode(lil));
            }
        }else if((headPersonFound!=null)&&anyLilsFound){
          for(Person lil:lilpeople){
          if(findOrNullPersonNode(lil)!=null){
            headPersonFound.addLittle(findOrNullPersonNode(lil));
            for(FamilyTree tree:familyForest.getFamilyForest()){
              if(tree.getRoot()==findOrNullPersonNode(lil)){
                familyForest.removeFamilyTree(tree);
              }
            }
          }else{
            headPersonFound.addLittle(new FamilyTreeNode(lil));
          }
            }
        }else if((headPersonFound==null)&&anyLilsFound){
          headPersonFound=new FamilyTreeNode(mainPerson);
          familyForest.addFamilyTree(new FamilyTree(headPersonFound));
          for(Person lil:lilpeople){
          if(findOrNullPersonNode(lil)!=null){
            headPersonFound.addLittle(findOrNullPersonNode(lil));
            for(FamilyTree tree:familyForest.getFamilyForest()){
              if(tree.getRoot()==findOrNullPersonNode(lil)){
                familyForest.removeFamilyTree(tree);
              }
            }
          }else{
            headPersonFound.addLittle(new FamilyTreeNode(lil));
          }
            }
        }else if((headPersonFound==null)&&(!anyLilsFound)){
          headPersonFound=new FamilyTreeNode(mainPerson);
          familyForest.addFamilyTree(new FamilyTree(headPersonFound));
          for(Person lil:lilpeople){
              headPersonFound.addLittle(new FamilyTreeNode(lil));
            }
        }
          
      } catch (NumberFormatException e) {
          System.out.println("Error parsing graduation year: " + e.getMessage());
      }
  }

  private FamilyTreeNode findOrNullPersonNode(Person person) {
      FamilyTreeNode personNode = familyForest.findPersonInForest(p -> p.equals(person));
      return personNode;
  }


    private Person createPersonFromEntries(List<String> entries, int startIndex) {
        return new Person(
            entries.get(startIndex),
            entries.get(startIndex + 1),
            Integer.parseInt(entries.get(startIndex + 2).trim()),
            entries.get(startIndex + 3)
        );
    }

    public String printFamilyForest() {
        ArrayList<Map<String, Object>> forest = new ArrayList<>();
        for (FamilyTree tree : familyForest.getFamilyForest()) {
            Map<String, Object> treeMap = convertTreeToMap(tree.getRoot());
            forest.add(treeMap);
        }
        try {
            return objectMapper.writeValueAsString(forest);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    private Map<String, Object> convertTreeToMap(FamilyTreeNode node) {
        Map<String, Object> nodeMap = new HashMap<>();
        nodeMap.put("name", getUniqueNodeName(node));
        List<Map<String, Object>> children = new ArrayList<>();
        for (FamilyTreeNode childNode : node.getLittles()) {
            children.add(convertTreeToMap(childNode));
        }
        nodeMap.put("children", children);
        return nodeMap;
    }
    
    private String getUniqueNodeName(FamilyTreeNode node) {
      Person person = node.getPerson();
      String major = person.getMajor();
      String newMajor = translateMajorName(major);
  
      return person.getFirstName() + " " + person.getLastName() + ", " + newMajor +" '" + person.getGradYear();
  }
  
  private String translateMajorName(String major) {
      switch (major) {
          case "Aerospace_Engineering":
              return "Aerospace Engineering";
          case "African_American_Studies":
              return "African American Studies";
          case "Agricultural_Resource_Economics":
              return "Agricultural & Resource Economics";
          case "Agricultural_Science_Technology":
              return "Agricultural Science & Technology";
          case "Atmospheric_Oceanic_Science":
              return "Atmospheric & Oceanic Science";
          case "Biochemical_Engineering":
              return "Biochemical Engineering";
          case "Biocomputational_Engineering":
              return "Biocomputational Engineering";
          case "Cinema_Media_Studies":
              return "Cinema & Media Studies";
          case "Civil_Engineering":
              return "Civil Engineering";
          case "Community_Health":
              return "Community Health";
          case "Criminology_Criminal_Justice":
              return "Criminology & Criminal Justice";
          case "Cyber_Physical_Systems_Engineering":
              return "Cyber-Physical Systems Engineering";
          case "Early_Childhood_Early_Childhood_Special_Education":
              return "Early Childhood & Early Childhood Special Education";
          case "Electrical_Engineering":
              return "Electrical Engineering";
          case "Elementary_Middle_Special_Education":
              return "Elementary/Middle Special Education";
          case "Environmental_Science_Policy":
              return "Environmental Science & Policy";
          case "Environmental_Science_Technology":
              return "Environmental Science & Technology";
          case "Fire_Protection_Engineering":
              return "Fire Protection Engineering";
          case "French_Language_Literature":
              return "French Language & Literature";
          case "Geographical_Sciences":
              return "Geographical Sciences";
          case "German_Studies":
              return "German Studies";
          case "Government_and_Politics":
              return "Government and Politics";
          case "Hearing_Speech_Sciences":
              return "Hearing & Speech Sciences";
          case "Human_Development":
              return "Human Development";
          case "Immersive_Media_Design":
              return "Immersive Media Design";
          case "Information_Science":
              return "Information Science";
          case "Information_Systems":
              return "Information Systems";
          case "International_Business":
              return "International Business";
          case "Italian_Studies":
              return "Italian Studies";
          case "Jewish_Studies":
              return "Jewish Studies";
          case "Landscape_Architecture":
              return "Landscape Architecture";
          case "Letters_Sciences":
          	return "Letters & Sciences";
          case "Materials_Science_Engineering":
              return "Materials Science & Engineering";
          case "Mechanical_Engineering":
              return "Mechanical Engineering";
          case "Mechatronics_Engineering":
              return "Mechatronics Engineering";
          case "Middle_School_Education_Mathematics_Science":
              return "Middle School Education - Mathematics & Science";
          case "Music_Liberal_Arts_Program":
              return "Music: Liberal Arts Program";
          case "Music_Professional_Program":
              return "Music: Professional Program";
          case "Nutrition_Food_Science":
              return "Nutrition & Food Science";
          case "Operations_Management_Business_Analytics":
              return "Operations Management & Business Analytics";
          case "Philosophy_Politics_Economics":
              return "Philosophy, Politics & Economics";
          case "Real_Estate_the_Built_Environment":
              return "Real Estate & the Built Environment";
          case "Religions_of_the_Ancient_Middle_East":
              return "Religions of the Ancient Middle East";
          case "Russian_Language_Literature":
              return "Russian Language & Literature";
          case "Secondary_Education_English":
              return "Secondary Education - English";
          case "Secondary_Education_Mathematics":
              return "Secondary Education - Mathematics";
          case "Secondary_Education_Science":
              return "Secondary Education - Science";
          case "Secondary_Education_Social_Studies":
              return "Secondary Education - Social Studies";
          case "Secondary_Education_World_Languages":
              return "Secondary Education - World Languages";
          case "Spanish_Language_Literatures_Culture":
              return "Spanish Language, Literatures & Culture";
          case "Supply_Chain_Management":
              return "Supply Chain Management";
          case "Technology_Information_Design":
              return "Technology & Information Design";
          case "Women_Gender_Sexuality_Studies":
              return "Women, Gender, & Sexuality Studies";
          default:
              return major.replace('_', ' ');
      }
  }
  
}
