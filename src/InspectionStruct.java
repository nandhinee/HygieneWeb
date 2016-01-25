public class InspectionStruct {
	String inspection_id;
	String name;
	String type;
	String address;
	String zipcode;
	String result;
	String comments;
	String location;
	int i=0;
	// Constructor to define fields for an inspection record
	public InspectionStruct(String input) {
		// TODO Auto-generated constructor stub
		
		String[] str = input.split("\\t");
		
		i++;
		if (str.length == 8) {
			this.inspection_id = str[0];
			this.name = str[1];
			this.type=str[2];
			this.address = str[3];
			this.zipcode = str[4];
			this.result = str[5];
			this.comments = str[6];
			this.location = str[7];
		}
		else if(str.length==7){
			this.inspection_id = str[0];
			this.name = str[1];
			this.type= str[2];
			this.address = str[3];
			this.zipcode = str[4];
			this.result = str[5];
			this.location = str[6];
		}
		
	}

}
