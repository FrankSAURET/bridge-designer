/*
 * TubeCrossSection.java  
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

/**
 * Representation of member tube stock.
 * 
 * @author Eugene K. Ressler
 */
class TubeCrossSection extends CrossSection {

    /**
     * Construct a tube cross section object.
     */
    public TubeCrossSection() {
        super(1, "Tube creux", "Tube");
    }

    /**
     * Get an array of all tube cross sections indexed by size.
     * 
     * @return shapes of all tube cross sections
     */
    @Override public Shape[] getShapes() {
        int nSizes = widths.length;
        Shape[] s = new Shape[nSizes];
        for (int sizeIndex = 0; sizeIndex < nSizes; sizeIndex++) {
            int width = widths[sizeIndex];
            int height = width;
            int thickness = Math.max(width / 20, 2);
            double area = (Utility.sqr(width) - Utility.sqr(width - 2 * thickness)) * 1e-6;
            double moment = (Utility.p4(width) - Utility.p4(width - 2 * thickness)) / 12 * 1e-12;
            s[sizeIndex] = new Shape(this, sizeIndex, String.format("%dx%dx%d", width, width, thickness), width, height, area, moment, thickness);
        }
        return s;
    }
}
