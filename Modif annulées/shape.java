/**
     * Construct a new solid rectangular bar shape with given parameters.
     * 
     * @param section cross-section
     * @param sizeIndex size index
     * @param name name
     * @param width width in millimeters
     * @param epaisseur epaisseur in millimeters
     * @param area cross-sectional area
     * @param moment moment
     */
    public Shape(CrossSection section, int sizeIndex, String name, double width, double area, double moment, double epaisseur) {
        this(section, sizeIndex, name, width, area, moment, epaisseur, width);
    } 

        /**
     * Return the epaisseur of the section millimeters.
     * 
     * @return epaisseur of the section in millimeters
     */
    public double getEpaisseur() {
        return epaisseur;
    }


private final double epaisseur;