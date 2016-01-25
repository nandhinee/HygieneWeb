<%@ page import = "java.util.ArrayList"%>
<%@ page import = "java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/HygieneWeb/Test.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Hygiene</title>
	
	<style type="text/css">
	      html, body, #map-canvas { height: 100%; margin: 0; padding: 0;}
	</style>
	<script src="http://maps.googleapis.com/maps/api/js">
	</script>
	
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
	
	<script type="text/javascript">
		<!-- This function marks the restaurants on map -->
		function initialize() {		
		  var mapProp = {
		    center:new google.maps.LatLng(41.881832,-87.623177),
		    zoom:13,
		    mapTypeId:google.maps.MapTypeId.ROADMAP
		  };
		  var map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
		  <% if (request.getSession().getAttribute("result") != null){
			  ArrayList<String> result1 = (ArrayList<String>)request.getSession().getAttribute("result");
				int num=0;
				for (String temp1: result1){
					//temp1 name, addr | loc
					String[] nameArr = temp1.split("\\|");
					//namArr[0] address, nameArr[1]-loc
					String nameAddress= nameArr[0];
					String[] name_address= nameAddress.split(",");
					String name= name_address[0];
					String loc = nameArr[1];
					String[] brk = loc.split(",");
					num++;
					double lat = Double.parseDouble(brk[0]);
					double lon = Double.parseDouble(brk[1]);
		  %>
					var lat1=<%=lat%>;
					
					var lon1=<%=lon%>;
					
					var name = <%=num%>;
					
					var marker=new google.maps.Marker({position:new google.maps.LatLng(lat1,lon1),title:name.toString(),});
		
					marker.setMap(map);
		  <%   
				}
		  	  } 
		  %>
			
		}
	
	</script>

</head>

<body onLoad="initialize()" style="color:#609;">
	<div id="logo" style="" align="center">
	  <form id="form2" name="form2">
	    <input type="image" name="logo-image" id="logo-image" src="/HygieneWeb/resources/restaurant.jpg" height="100px" width="200px" />
	    
	  <b>Hygienic Restaurant Search</b>
	  </form>
	
	</div>
	
	
	<div id="InputForm" align="center">
	      <form id="form1" name="form1" action="FoodInspec">
	        <p>
	          <label for="zipcode" style="font-family:Arial, Helvetica, sans-serif">Enter Zipcode:</label>
	          <input type="text" name="zipcode" id="zipcode" class="input" />
	          <input type="submit" name="Submit" id="Submit" value="Submit" class="button" />
	        </p>
	        <p>&nbsp;</p>
	      </form>
	</div>
	
	<div id="page-content" style="background: #9CF; background:#399">		
		<div class="table" id="table">
			<!-- Display list of recommended restaurant -->
	    	<table id ="customers">
	        	<tr>
	        		<th scope="row" ><b>Serial ID</b></th>
	          		<th scope="row" style="width: 400px"><b>RESTAURANTS</b></th>
	        	</tr>
	
			
				<%
					if (request.getSession().getAttribute("result") != null){
						ArrayList<String> result = (ArrayList<String>)request.getSession().getAttribute("result");
						int count = 0;					
						for (String temp: result){
							count++;
							String[] rest = temp.split("\\|");
							String r = rest[0];
				%>	
								<tr>
									<td scope="row">&nbsp;<%=count%></td>
									<td scope="row" style="width: 400px">&nbsp;<%=r%></td>	
								</tr> 
				<%		}
						request.getSession().removeAttribute("result");
					}
				%>
				
			 </table>
	    </div>
	  
	    <!-- <input type="hidden" id="h1" value=""> -->
	    <div id="googleMap" style="width:750px;height:500px;"></div>
	</div> 
</body>

</html>