/*
 * BielleCrossSection.java  
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
/**
 * Representation of member Bielle stock.
 * 
 * @author Frank SAURET
 */
package bridgedesigner;


class BielleCrossSection extends CrossSection {
     /**
     * Construct a bielle cross section object.
     */
    public BielleCrossSection() {
        super(2, "Barre pleine rectangulaire", "Rect");
    }

    /**
     * Get an array of all Bielle sections indexed by size.
     * 
     * @return shapes of all Bielle sections
     */
    @Override
    public Shape[] getShapes() {
        int nSizes = bielleHeights.length; //Nombre de valeurs
        Shape[] s = new Shape[nSizes];
        for (int sizeIndex = 0; sizeIndex < nSizes; sizeIndex++) {
            int height = bielleHeights[sizeIndex];
            int width = 50;
            double area = height * width * 1e-6;// Section en m²
            double quadra=height* height;
            quadra=quadra*height;
            quadra=quadra*width;
            double moment = (quadra/12) * 1e-12; //Moment quadratique section rectangulaire en mètre puissance 4
            s[sizeIndex] = new Shape(this, sizeIndex, String.format("%dx%d", width, height), height, width, area, moment);
        }
        return s;
    }
}
