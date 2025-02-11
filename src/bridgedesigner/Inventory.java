/*
 * Inventory.java  
 *   
 * Copyright (C) 2008 Eugene K. Ressler
 *   
 * This program is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  
 * GNU General Public License for more details.  
 *   
 * You should have received a copy of the GNU General Public License  
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.  
 */

package bridgedesigner;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * Inventory of stock for building bridges and supporting functions.
 * 
 * @author Eugene K. Ressler
 */
public class Inventory {

    /**
     * Bit returned by <code>getAllowedShapeChanges()</code> to say there is room to increase its size.
     */
    public static final int SHAPE_INCREASE_SIZE = 1;
    /**
     * Bit returned by <code>getAllowedShapeChanges()</code> to say there is room to decrease its size.
     */
    public static final int SHAPE_DECREASE_SIZE = 2;
    /**
     * Compression resistance factor characteristic of material.
     */
    public static final double compressionResistanceFactor = 0.90;
    /**
     * Tension resistance factor characteristic of material.
     */
    public static final double tensionResistanceFactor = 0.95;
    /**
     * Cost for ordering each unique material-shape in the bridge.
     */
    private static final double orderingFee = 1000.00;
    /**
     * Cost of a connection in the bridge.
     */
    private static final double connectionFee = 400.00;
    
    /**
     * Table of cross-sections in this inventory.
     */
    private final CrossSection crossSections[] = new CrossSection[]{
        new BarCrossSection(),
        new TubeCrossSection(),
        new BielleCrossSection(),
    };
    
    /**
     * Table of materials in this inventory.
     * index, nom, nom court, module d'élasticité (E) en kPa, limite d'élasticité (Fy) kPa, masse volumique (rho), cout par kilo de barre, tubes, bielle
     *  Carton Canson C200704250, Car, E= 3 à 9 GPa, Fy=15 à 34 MPa, ρ=618 kg/m³, 21,6 €/kg ou 13,2 €/m² TTC
     *  Médium , MDF, E= 2,5 GPa, Fy=650 kPa, ρ=740 kg/m³, 3,3 €/kg ou 7,35 €/m² (en 3 mm) TTC
     * new Material(3, "Carton 1mm", "Car", 3000000, 15000, 618, new double[]{21.6, 21.6}),
     * new Material(4, "MDF 3mm", "MDF", 2500000, 650, 740, new double[]{3.30, 3.30}),
     * 
     * Modifier si nécessaire dans materiaux.xml.
     */             
    private Material[] materials;
    private void loadMaterialsFromXML() {
    String fichierMateriaux=System.getProperty("user.dir")+System.getProperty("file.separator")+"materiaux.xml";
    
    try {
        File materiauxXML = new File(fichierMateriaux);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(materiauxXML);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("material");
        materials = new Material[nList.getLength()];
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                String abbreviation = eElement.getElementsByTagName("abbreviation").item(0).getTextContent();
                int youngModulus = Integer.parseInt(eElement.getElementsByTagName("youngModulus").item(0).getTextContent());
                int shearModulus = Integer.parseInt(eElement.getElementsByTagName("elasticityLimit").item(0).getTextContent());
                int density = Integer.parseInt(eElement.getElementsByTagName("density").item(0).getTextContent());
                double[] costs = new double[3];
                costs[0] = Double.parseDouble(eElement.getElementsByTagName("cost").item(0).getTextContent());
                costs[1] = Double.parseDouble(eElement.getElementsByTagName("cost").item(1).getTextContent());
                costs[2] = Double.parseDouble(eElement.getElementsByTagName("cost").item(2).getTextContent());
                materials[temp] = new Material(id, name, abbreviation, youngModulus, shearModulus, density, costs);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    /**
     * Table of shapes in this inventory.
     */
    private final Shape shapes[][] = new Shape[crossSections.length][];
    
    /**
     * Construct a stock and a material inventory.
     */
    public Inventory() {
        for (int i = 0; i < crossSections.length; i++) {
            shapes[i] = crossSections[i].getShapes();
        }
        loadMaterialsFromXML();
    }

    /**
     * Return compressive strength of a given material and shape of a given length.
     * 
     * @param material material
     * @param shape shape
     * @return strength
     */
    public static double compressiveStrength(Material material, Shape shape, double length) {
            final double Fy = material.getFy();
            final double area = shape.getArea();
            final double E = material.getE();
            final double moment = shape.getMoment();
            double lambda = length * length * Fy * area / (9.8696044 * E * moment);
            return (lambda <= 2.25) ? 
                compressionResistanceFactor * Math.pow(0.66, lambda) * Fy * area : 
                compressionResistanceFactor * 0.88 * Fy * area / lambda;
    }

    /**
     * Return tensile strength of a given material and shape.
     * 
     * @param material material
     * @param shape shape
     * @return strength
     */
    public static double tensileStrength(Material material, Shape shape) {
        return tensionResistanceFactor * material.getFy() * shape.getArea();
    }
    
    /**
     * Return the connection fee. Made this a method in case the cost model gets fancier in the future.
     * 
     * @return connection fee
     */
    public double getConnectionFee() {
        return connectionFee;
    }

    /**
     * Return the ordering fee. Made this a method in case the cost model gets fancier in the future.
     * 
     * @return ordering fee
     */
    public double getOrderingFee() {
        return orderingFee;
    }
    
    /**
     * Return the material with given index.
     * 
     * @return material
     */
    public Material getMaterial(int index) {
        return materials[index];
    }
    
    /**
     * Return the number of shapes in the inventory that have a given section.
     * 
     * @param sectionIndex index of the section of the shape.
     * @return number of shapes
     */
    public int getNShapes(int sectionIndex) {
        return shapes[sectionIndex].length;
    }
    
    /**
     * Return the inventory's shape with given section and sizee indices.
     * 
     * @param sectionIndex index of section
     * @param sizeIndex index of size
     * @return shape
     */
    public Shape getShape(int sectionIndex, int sizeIndex) {
        return shapes[sectionIndex][sizeIndex];
    }
    
    /**
     * Return a shape that is one bigger or one smaller than a reference
     * shape based on the given increment. Other shape attributes remain the same.  
     * If no increment/decrement is possible, the reference shape is returned.
     * 
     * @param reference reference shape
     * @param sizeIncrement +1 for one bigger, -1 for one smaller
     * @return shape that is one bigger or one smaller than reference
     */
    public Shape getShape(Shape reference, int sizeIncrement) {
        int sectionIndex = reference.getSection().getIndex();
        int newSizeIndex = Math.min(shapes[sectionIndex].length - 1, 
                Math.max(0, reference.getSizeIndex() + sizeIncrement));
        return shapes[sectionIndex][newSizeIndex];
    }

    /**
     * Return a bit mask indicating whether it's possible to increase and/or decrease the size of the given
     * reference shape. See <code>SHAPE_DECREASE_SIZE</code> and <code>SHAPE_INCREASE_SIZE</code> for mask 
     * bit values.
     * 
     * @param shape reference shape
     * @return bit mask
     */
    public int getAllowedShapeChanges(Shape shape) {
        int sizeIndex = shape.getSizeIndex();
        int mask = 0;
        if (sizeIndex > 0) {
            mask |= SHAPE_DECREASE_SIZE;
        }
        if (sizeIndex < shapes[shape.getSection().getIndex()].length - 1) {
            mask |= SHAPE_INCREASE_SIZE;
        }
        return mask;
    }
    
    /**
     * Return a list combobox model containing materials in this inventory.  THis is used by the stock selector
     * combobox component for materials.
     * 
     * @return combobox model
     */
    public ComboBoxModel getMaterialBoxModel() {
        return new MaterialBoxModel(false);
    }

    /**
     * Return a list combobox model containing materials in this inventory.  This is used by the stock selector
     * combobox component for materials.
     * 
     * @param shortForm whether short form of material names should be used in the model
     * @return material combobox model
     */
    public ComboBoxModel getMaterialBoxModel(boolean shortForm) {
        return new MaterialBoxModel(shortForm);
    }

    /**
     * Return a list combobox model containing sections in this inventory.  This is used by the stock selector
     * combobox component for sections.
     * 
     * @return section combobox model
     */
    public ComboBoxModel getSectionBoxModel() {
        return new SectionBoxModel(false);
    }
    
    /**
     * Return a list combobox model containing sections in this inventory.  This is used by the stock selector
     * combobox component for sections.
     * 
     * @param shortForm whether short form of material names should be used in the model
     * @return section combobox model
     */
    public ComboBoxModel getSectionBoxModel(boolean shortForm) {
        return new SectionBoxModel(shortForm);
    }
    
    /**
     * Return a list combobox model containing sizes in this inventory.  This is used by the stock selector
     * combobox component for sizes.
     * 
     * @return section combobox model
     */
    public ComboBoxModel getSizeBoxModel() {
        return new SizeBoxModel(false);
    }
    
    /**
     * Return a list combobox model containing sizes in this inventory.  This is used by the stock selector
     * combobox component for sizes.
     * 
     * @param shortForm whether short form of material names should be used in the model
     * @return section combobox model
     */
    public ComboBoxModel getSizeBoxModel(boolean shortForm) {
        return new SizeBoxModel(shortForm);
    }
    
    private class MaterialBoxModel extends AbstractListModel implements ComboBoxModel {

        private Object selectedObject;
        private boolean shortForm;

        public MaterialBoxModel(boolean shortForm) {
            this.shortForm = shortForm;
        }
        
        public int getSize() {
            return materials.length;
        }

        public Object getElementAt(int index) {
            return shortForm ? materials[index].getShortName() : materials[index].getName();
        }

        public void setSelectedItem(Object anObject) {
            if (!Utility.areEqual(anObject, selectedObject)) {
                selectedObject = anObject;
                fireContentsChanged(this, -1, -1);
            }
        }

        public Object getSelectedItem() {
            return selectedObject;
        }
    }

    private class SectionBoxModel extends AbstractListModel implements ComboBoxModel {

        private Object selectedObject;
        private boolean shortForm;

        public SectionBoxModel(boolean shortForm) {
            this.shortForm = shortForm;
        }
        
        public int getSize() {
            return crossSections.length;
        }

        public Object getElementAt(int index) {
            return shortForm ? crossSections[index].shortName : crossSections[index].name;
        }

        public void setSelectedItem(Object anObject) {
            if (!Utility.areEqual(anObject, selectedObject)) {
                selectedObject = anObject;
                fireContentsChanged(this, -1, -1);
            }
        }

        public Object getSelectedItem() {
            return selectedObject;
        }
    }

    /**
     * Specialized combobox model for material sizes.
     */
    public class SizeBoxModel extends AbstractListModel implements ComboBoxModel {

        private int sectionIndex = 0;
        private boolean shortForm;
        private int selectedIndex = -1;

        /**
         * Construct a specialzed combobox model for sizes corresponding to a given section.
         * 
         * @param shortForm whether to use short form for text representations of sizes
         */
        public SizeBoxModel(boolean shortForm) {
            this.shortForm = shortForm;
        }
        
        /**
         * Return the index of the section that this size list corresponds to.
         * 
         * @return section index
         */
        public int getSectionIndex() {
            return sectionIndex;
        }

        /**
         * Set the index of the section that this size list corresponds to.
         * 
         * @param newSectionIndex section index
         */
        public void setSectionIndex(int newSectionIndex) {
            if (newSectionIndex != sectionIndex) {
                int newLen = (newSectionIndex == -1) ? 0 : shapes[newSectionIndex].length;
                int oldLen = (sectionIndex == -1) ? 0 : shapes[sectionIndex].length;
                
                // ensure selection index is still valid
                if (selectedIndex >= newLen)
                    selectedIndex = newLen - 1;
                
                // change section index here so content change handlers see it
                sectionIndex = newSectionIndex;
                
                // fire events to cause changes in the instance
                if (newLen > oldLen) {
                    fireIntervalAdded(this, oldLen, newLen - 1);
                    fireContentsChanged(this, 0, oldLen - 1);
                }
                else if (newLen < oldLen) {
                    fireIntervalRemoved(this, newLen, oldLen - 1);
                    fireContentsChanged(this, 0, newLen - 1);
                }
                else {
                    fireContentsChanged(this, 0, newLen - 1);
                }
            }
        }
        
        /**
         * Return the number of items in the size list.
         * 
         * @return number of items in the list
         */
        public int getSize() {
            return (sectionIndex == -1) ? shapes[0].length : shapes[sectionIndex].length;
        }

        /**
         * Return the index'th element in the list.
         * 
         * @param index index
         * @return string representation of shape's size with length 
         * dependent on constructor's <code>shortForm</code> parameter.
         */
        public Object getElementAt(int index) {
            int si = (sectionIndex == -1) ? 0 : sectionIndex;
            return shortForm ? shapes[si][index].getNominalWidth() : shapes[si][index].getName();
        }

        /**
         * Return the index of a given shape in the list.
         * 
         * @param element element to look up
         * @return index of string
         */
        public int indexOf(Object element) {
            for (int i = 0; i < getSize(); i++) {
                if (Utility.areEqual(element, getElementAt(i))) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Set the selected shape in the list.
         * 
         * @param element shape to select
         */
        public void setSelectedItem(Object element) {
            if (!Utility.areEqual(element, getSelectedItem())) {
                int newSelectedIndex = indexOf(element);
                if (newSelectedIndex != selectedIndex) {
                    selectedIndex = newSelectedIndex;
                    fireContentsChanged(this, -1, -1);
                }
            }
        }

        /**
         * Return the selected shape or null if none.
         * 
         * @return shape
         */
        public Object getSelectedItem() {
            return (selectedIndex >= 0) ? getElementAt(selectedIndex) : null;
        }
    } 

}
