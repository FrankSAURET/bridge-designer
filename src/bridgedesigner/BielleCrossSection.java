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
        super(2, "Barre pleine rectangulaire", "Bielle");
    }

    /**
     * Get an array of all Bielle sections indexed by size.
     * 
     * @return shapes of all Bielle sections
     */
    @Override
    public Shape[] getShapes() {
        int nSizes = bielleWidths.length; //Nombre de valeurs
        Shape[] s = new Shape[nSizes];
        for (int sizeIndex = 0; sizeIndex < nSizes; sizeIndex++) {
            int width = bielleWidths[sizeIndex];
            int epaisseur = 50;
            double area = epaisseur * width * 1e-6;// Section en m²
            double moment = (width* width* width * epaisseur)/12 * 1e-12; //Moment quadratique section rectangulaire en mètre cube
            s[sizeIndex] = new Shape(this, sizeIndex, String.format("%dx%d", width, epaisseur), width, epaisseur, area, moment);
        }
        return s;
    }
}
