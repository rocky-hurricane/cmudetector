**Environmen Setup**

	1. Program location: https://github.com/andreaiacono/OpenCVDemo
		jdk1.8, maven, github is required, you can google how to install
	2. Download openCV 3.1.0: https://github.com/opencv/opencv/archive/3.1.0.zip
	Download openCV_contrib 3.1.0: https://github.com/opencv/opencv_contrib/releases/tag/3.1.0
	Extract the archives to the same folder (/Library/java  is recommended ) 
	3. open file opencv_contrib-3.1.0/modules/face/CMakeLists.txt
	append "java" to the WRAP section, so that it reads:?ocv_define_module(face opencv_core opencv_imgproc opencv_objdetect WRAP python java)
	4. Move to opencv-3.1.0 and Screate an empty folder named build
		cd /Library/java/opencv-3.1.0
		mkdir build
	then move to?build?directory and generate makefile:
		cmake -DBUILD_SHARED_LIBS=OFF ..
	5. Notice that when generating the makefile, the output in the Java field looks like this:
	
	That is, there is a specified ant and JNI path, and Java wrappers is set to YES.(ant and JNI are expected pre-installed)
	cmake -D OPENCV_EXTRA_MODULES_PATH=/Library/Java/opencv-3.1.0/modules ..
	6. Then build openCV to create jar file
		cd build
		make -j8
	when finished, make sure the files?opencv-310.jar  created in the?build/bin?directory.
	7. create a soft link with .dylib extension for the .so file. 
		ln?-s?libopencv_java310.so?libopencv_java310.dylib
	8. In intellij, specify the location of the library with vm argument.
	
	9. once compiled,  set the JAR as a library for the Java project.


https://github.com/andreaiacono/OpenCVDemo
https://elbauldelprogramador.com/en/compile-opencv-3.2-with-java-intellij-idea/
http://opencv-java-tutorials.readthedocs.io/en/latest/index.html
