package utility;

public class TestIndexOf {

	
	
	public static void main(String[] args) {
		
	String value = "H1	-84146\r\n" + 
			"H2	-24161\r\n" + 
			"H3	0\r\n" + 
			"H4	0\r\n" + 
			"H5	0\r\n" + 
			"H6	-5985019\r\n" + 
			"H7	4\r\n" + 
			"H8	60273\r\n" + 
			"H9	397304\r\n" + 
			"H10	248\r\n" + 
			"H11	0\r\n" + 
			"H12	0\r\n" + 
			"H17	7828\r\n" + 
			"H18	7970\r\n" + 
			"Checksum	z\r\n" + 
			"PID	0x203\r\n" + 
			"V	59248\r\n" + 
			"I	0\r\n" + 
			"P	0\r\n" + 
			"CE	-23745\r\n" + 
			"SOC	909\r\n" + 
			"TTG	-1\r\n" + 
			"Alarm	OFF\r\n" + 
			"Relay	OFF\r\n" + 
			"AR	0\r\n" + 
			"BMV	700\r\n" + 
			"FW	0308\r\n" + 
			"Checksum	�\r\n" + 
			"H1	-84146\r\n" + 
			"H2	-24161\r\n" + 
			"H3	0\r\n" + 
			"H4	0\r\n" + 
			"H5	0\r\n" + 
			"H6	-5985019\r\n" + 
			"H7	4\r\n" + 
			"H8	60273\r\n" + 
			"H9	397305\r\n" + 
			"H10	248\r\n" + 
			"H11	0\r\n" + 
			"H12	0\r\n" + 
			"H17	7828\r\n" + 
			"H18	7970\r\n" + 
			"Checksum	y\r\n";
				
		
		System.out.println(value.lastIndexOf("Checksum"));
		
		
	}
	
}
