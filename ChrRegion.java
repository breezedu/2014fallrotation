package rotation2014fall;


/*********
 * Create a Chromatin region object, for Chr2L first;
 * Each ChrRegion was read from the Kc-EarlyDomains.bed or Kc-LateDomains.bed files;
 * Initially, the ChrRegion.read of each ChrRegion is '0'; Store all reads into Chr2L_early arrayList
 * and Chr2L_late arrayList.
 * 
 * Everytime we scan a read from BED_DM433_2L_output_t_922.txt document, we will compare the read_in
 * data with the Chr2L_early and Chr2L_Late arrayLists, if the read_in chromatin's start value matches any
 * ChrRegion in Chr2L_early or Chr2L_late arrayLists, update the ChrRegion.read++;
 * 
 * @author Jeff
 *
 */
class ChrRegion{
	
	String name = "";
	long start = 0;
	long end = 0;
	int read = 0;
}