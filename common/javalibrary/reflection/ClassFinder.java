package javalibrary.reflection;

import java.io.File;
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
	
	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 * @param packageName The base package
	 * @return The classes in the package
	 */
	private static ArrayList<Class<?>> getClasses(String packageName) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		    String path = packageName.replace('.', '/');
		    Enumeration<URL> resources = classLoader.getResources(path);
		    List<File> dirs = new ArrayList<File>();
		    
		    while(resources.hasMoreElements()) {
		        URL resource = resources.nextElement();
		        dirs.add(new File(resource.getFile()));
		    }
		    
		    ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		    for(File directory : dirs) {
		        classes.addAll(findClasses(directory, packageName));
		    }
	
		    return classes;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Class<?>>();
		}
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
