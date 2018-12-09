package convertor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import GeoObjects.Fruit;
import GeoObjects.Packman;
import GeoObjects.Point3D;
import algorithm.ShortestPathAlgorithm;
import gameObjects.Game;
import gameObjects.PathPoint;

public class Game2kml {
	
	private Game game;
	private StringBuilder kml;
	private String path;
	private String name;
	public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
	
	public void export(Game game, String path, String name) {
		this.game = game;
		this.path = path;
		this.name = name;
		
		ShortestPathAlgorithm algo = new ShortestPathAlgorithm(game);
		algo.multiPackmans();
		write();
		
		PrintWriter pw = null;
		
		try 
		{
			pw = new PrintWriter(new File(path + "\\" + name + ".kml"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
	
		pw.write(kml.toString());
		pw.close();
		System.out.println("done!"); // printing in the console a "done" message.
	}
	
	private void write() {
		writeOpen();
		writeFruits();
		writePackmans();
		writeEnd();
	}

	private void writeOpen() {
		kml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); 
		kml.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style>");
		kml.append("<name>"+name+"</name>\n");
	}

	private void writeFruits() {
		kml.append("<Folder><name>fruits</name>\n");
		for (Fruit fruit: game.fruits) {
			kml.append("<Placemark>\n");

			kml.append("<name><![CDATA[fruit"+ fruit.getId() +"]]></name>\n");
			kml.append("<description><![CDATA[");
			kml.append("weight: <b>" + fruit.getWeight() +"</b><br/>");
			kml.append("]]></description><styleUrl>#red</styleUrl>\n");
					
			kml.append("<Point>\n");
			kml.append("<coordinates>"+ fruit.getLocation().x()+", "+ fruit.getLocation().y()+", "+fruit.getLocation().z()+
			"</coordinates>\n");
			kml.append("<altitudeMode>clampToGround" + "</altitudeMode></Point>\n");
			kml.append("</Placemark>\n");
		}
		kml.append("</folder>\n");
	}

	private void writePackmans() {
		writeStyle();
		kml.append("<Folder>\n");
		
		for (Packman packman: game.packmans) {
			StringBuilder when = new StringBuilder();
			StringBuilder coord = new StringBuilder();
			StringBuilder weight = new StringBuilder();
			StringBuilder seconds = new StringBuilder();
			
			for (PathPoint point: packman.path) {
				when.append("<when>"+"2010-05-28T02:02:09Z"+"</when>\n"); //change it!!!!
				Point3D location = point.getLocation();
				coord.append("<gx:coord>"+location.x()+" "+location.y()+" "+location.z()+"</gx:coord>\n");
				weight.append("<gx:value>"+point.getWeight()+"</gx:value>\n");
				seconds.append("<gx:value>"+point.getSeconds()+"</gx:value>\n");
			}
			
			kml.append("<name>packman"+packman.id+"</name>\n");
			kml.append("<styleUrl>#multiTrack</styleUrl>\n");
			kml.append("<gx:Track>\n");
			
			kml.append(when);
			kml.append(coord);
			
			kml.append("<ExtendedData>/n");
			kml.append("<SchemaData schemaUrl=\"#schema\">\n");
			
			kml.append("<gx:SimpleArrayData name=\"we\">\n");
			kml.append(weight);
			kml.append("</gx:SimpleArrayData>\n");
			
			kml.append("<gx:SimpleArrayData name=\"sec\">\n");
			kml.append(seconds);
			kml.append("</gx:SimpleArrayData>\n");
			
			kml.append("</SchemaData>\r\n" + 
					"          </ExtendedData>\r\n" + 
					"        </gx:Track>\r\n" + 
					"      </Placemark>");
		}
		
		kml.append("</Folder>");
	}

	private void writeStyle() {
		kml.append("<LookAt>\r\n" + 
				"      <gx:TimeSpan>\r\n" + 
				"        <begin>2010-05-28T02:02:09Z</begin>\r\n" + 
				"        <end>2010-05-28T02:02:56Z</end>\r\n" + 
				"      </gx:TimeSpan>\r\n" + 
				"      <longitude>-122.205544</longitude>\r\n" + 
				"      <latitude>37.373386</latitude>\r\n" + 
				"      <range>1300.000000</range>\r\n" + 
				"    </LookAt>\r\n" + 
				"    <Style id=\"track_n\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <scale>.5</scale>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://earth.google.com/images/kml-icons/track-directional/track-none.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"      </IconStyle>\r\n" + 
				"      <LabelStyle>\r\n" + 
				"        <scale>0</scale>\r\n" + 
				"      </LabelStyle>\r\n" + 
				"\r\n" + 
				"    </Style>\r\n" + 
				"    <!-- Highlighted track style -->\r\n" + 
				"    <Style id=\"track_h\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <scale>1.2</scale>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://earth.google.com/images/kml-icons/track-directional/track-none.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>\r\n" + 
				"    <StyleMap id=\"track\">\r\n" + 
				"      <Pair>\r\n" + 
				"        <key>normal</key>\r\n" + 
				"        <styleUrl>#track_n</styleUrl>\r\n" + 
				"      </Pair>\r\n" + 
				"      <Pair>\r\n" + 
				"        <key>highlight</key>\r\n" + 
				"        <styleUrl>#track_h</styleUrl>\r\n" + 
				"      </Pair>\r\n" + 
				"    </StyleMap>\r\n" + 
				"    <!-- Normal multiTrack style -->\r\n" + 
				"    <Style id=\"multiTrack_n\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://earth.google.com/images/kml-icons/track-directional/track-0.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"      </IconStyle>\r\n" + 
				"      <LineStyle>\r\n" + 
				"        <color>99ffac59</color>\r\n" + 
				"        <width>6</width>\r\n" + 
				"      </LineStyle>\r\n" + 
				"\r\n" + 
				"    </Style>\r\n" + 
				"    <!-- Highlighted multiTrack style -->\r\n" + 
				"    <Style id=\"multiTrack_h\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <scale>1.2</scale>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://earth.google.com/images/kml-icons/track-directional/track-0.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"      </IconStyle>\r\n" + 
				"      <LineStyle>\r\n" + 
				"        <color>99ffac59</color>\r\n" + 
				"        <width>8</width>\r\n" + 
				"      </LineStyle>\r\n" + 
				"    </Style>\r\n" + 
				"    <StyleMap id=\"multiTrack\">\r\n" + 
				"      <Pair>\r\n" + 
				"        <key>normal</key>\r\n" + 
				"        <styleUrl>#multiTrack_n</styleUrl>\r\n" + 
				"      </Pair>\r\n" + 
				"      <Pair>\r\n" + 
				"        <key>highlight</key>\r\n" + 
				"        <styleUrl>#multiTrack_h</styleUrl>\r\n" + 
				"      </Pair>\r\n" + 
				"    </StyleMap>\r\n" + 
				"    <!-- Normal waypoint style -->\r\n" + 
				"    <Style id=\"waypoint_n\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/pal4/icon61.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>\r\n" + 
				"    <!-- Highlighted waypoint style -->\r\n" + 
				"    <Style id=\"waypoint_h\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <scale>1.2</scale>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/pal4/icon61.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>\r\n" + 
				"    <StyleMap id=\"waypoint\">\r\n" + 
				"      <Pair>\r\n" + 
				"        <key>normal</key>\r\n" + 
				"        <styleUrl>#waypoint_n</styleUrl>\r\n" + 
				"      </Pair>\r\n" + 
				"      <Pair>\r\n" + 
				"        <key>highlight</key>\r\n" + 
				"        <styleUrl>#waypoint_h</styleUrl>\r\n" + 
				"      </Pair>\r\n" + 
				"    </StyleMap>\r\n" + 
				"    <Style id=\"lineStyle\">\r\n" + 
				"      <LineStyle>\r\n" + 
				"        <color>99ffac59</color>\r\n" + 
				"        <width>6</width>\r\n" + 
				"      </LineStyle>\r\n" + 
				"    </Style>\r\n" + 
				"    <Schema id=\"schema\">\r\n" + 
				"      <gx:SimpleArrayField name=\"heartrate\" type=\"int\">\r\n" + 
				"        <displayName>Heart Rate</displayName>\r\n" + 
				"      </gx:SimpleArrayField>\r\n" + 
				"      <gx:SimpleArrayField name=\"cadence\" type=\"int\">\r\n" + 
				"        <displayName>Cadence</displayName>\r\n" + 
				"      </gx:SimpleArrayField>\r\n" + 
				"      <gx:SimpleArrayField name=\"power\" type=\"float\">\r\n" + 
				"        <displayName>Power</displayName>\r\n" + 
				"      </gx:SimpleArrayField>\r\n" + 
				"    </Schema>");
	}

	private void writeEnd() {
		kml.append("</Document>");
		kml.append("</kml>");
	}

}