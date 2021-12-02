package project500.callgraph.soot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import soot.Body;
import soot.G;
import soot.PhaseOptions;
import soot.Scene;
import soot.jimple.spark.SparkTransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.options.Options;
import soot.toolkits.graph.DirectedGraph;
import soot.util.dot.DotGraph;
import soot.util.dot.DotGraphConstants;

import static soot.jimple.toolkits.thread.mhp.PegToDotFile.isBrief;

public class Main {
	public static void main(String[] args) throws IOException {

//		Call Graph creation

		String bin_path = Paths.get("bin").toAbsolutePath().toString();
		String jre_version = "1.6.0_45";
		String jre = Files.list(Paths.get("jre", jre_version)).map(p -> p.toAbsolutePath().toString()).collect(Collectors.joining(File.pathSeparator));
		String mainClass = "project500.callgraph.socket.Client";

		G.reset(); //reset soot
		long start_time = System.currentTimeMillis();

		Options.v().classes().add(mainClass); //set input class

		Options.v().set_soot_classpath(bin_path + File.pathSeparator + jre); // set class path

		Options.v().set_main_class(mainClass); //set the main class

		Options.v().set_whole_program(true); //to analyze whole program

		Scene.v().loadNecessaryClasses(); //load all required classes
		Scene.v().setMainClassFromOptions();

		useRTA(); //run RTA
//		useVTA(); //run VTA

		long end_time = System.currentTimeMillis(); //to calculate the end time
		long time = (end_time - start_time); //to calculate the total time

		createCallGraph(); //create the call graph

		System.out.println(time + " milliseconds."); //print the time it takes to create the call graph
	}

	//RTA
	private static void useRTA() {
		Map<String, String> opts = new HashMap<String, String>(PhaseOptions.v().getPhaseOptions("cg.spark"));
		opts.put("enabled", "true");
		opts.put("on-fly-cg", "false");
		opts.put("rta", "true");
		SparkTransformer.v().transform("", opts);
	}

	//VTA
	private static void useVTA() {
		Map<String, String> opts = new HashMap<String, String>(PhaseOptions.v().getPhaseOptions("cg.spark"));
		opts.put("enabled", "true");
		opts.put("vta", "true");
		SparkTransformer.v().transform("", opts);
	}

	//print the information with respect to call graph
	private static void createCallGraph() {
		CallGraph cg = Scene.v().getCallGraph();
		System.out.println("Edges " + cg.size() + " edges.");
		System.out.println("Affected Libraries " + Scene.v().getMainMethod());
		String check_affected_library_solution_regex = "java.lang.String";
		if(Scene.v().getMainMethod().toString().contains(check_affected_library_solution_regex))
		{
			System.out.println("Solution: Sanitize the inputs");
		}
		Iterator<Edge> edgesOutOf = cg.edgesOutOf(Scene.v().getMainMethod());
		while (edgesOutOf.hasNext()) {
			System.out.println(edgesOutOf.next());
		}
	}

}