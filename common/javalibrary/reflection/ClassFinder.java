package javalibrary.reflection;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Alex Barter
 * @since 2 Nov 2013
 */
public class ClassFinder {

	@SuppressWarnings("unchecked")
	public static <T> ArrayList<Class<T>> getAllClassesThatExtend(Class<T> fileImplement) {
		ArrayList<Class<?>> classes = ClassFinder.getClasses("");
		ArrayList<Class<T>> finalClasses = new ArrayList<Class<T>>();
		for(Class<?> cl : classes) {
			Class<?> superClass = cl;
			while(superClass != null && !superClass.isInterface()) {
				//If the superclass equals the class that it is suppose to extend add it to the list
				if(superClass.equals(fileImplement))
					finalClasses.add((Class<T>)superClass);
				superClass = superClass.getSuperclass();
			}
		}
		
		return finalClasses;
	}
	
	public static ArrayList<Class<?>> getClasses(String packageName) {
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		
		try {
			URI uri = ClassFinder.class.getResource(packageName).toURI();
			File folder = new File(uri);
			
			for(File file : folder.listFiles()) {
				if(file.isDirectory()) 
					list.addAll(getClasses(packageName + file.getName() + File.separator));
				else if(isClassFile(file)) {
					String fileName = packageName.substring(1, packageName.length()) + file.getName().substring(0, file.getName().length() - 6);
					fileName = fileName.replace(File.separator.charAt(0), '.');
					//list.add(Class.forName(fileName));
				}
					
					
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public static boolean isClassFile(File file) {
		return file.getName().endsWith(".class");
	}
	
	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 * @param directory The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 */
	private static ArrayList<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
	    if (!directory.exists())
	        return classes;
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        }
	        else if (file.getName().endsWith(".class")) {
	        	String fileName = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
	        	if(fileName.startsWith("."))
	        		fileName = fileName.substring(1);
	        	System.out.println(fileName);
	        	classes.add(Class.forName(fileName));
	        }
	    }
	    return classes;
	}
}
