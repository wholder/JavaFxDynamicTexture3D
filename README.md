<p align="center"><img src="https://github.com/wholder/JavaFxDynamicTexture3D/blob/master/images/JavaFxDynamicTexture3D.png"></p>

## JavaFxDynamicTexture3D
JavaFxDynamicTexture3D demonsrtates how to dynamically create an animated texture in JavaFx and then use it to texture the surface of cube.
        
Note: one curious artifact (at least in macOs) is that the animated bouncing ball displayed in the dynamic texture leaves a shadowy trail behind it as it moves that resembles motion blur.  While the effect is interesting in this case, it can cause problems for other types of textures you might want to generate dynamically.  I tried various methods to stop this from happening but, so far, I have not found a solution.

### Requirements
JavaFxDynamicTexture3D requires the Java 8 JRE, or later.  There is a [**Runnable JAR file**](https://github.com/wholder/JavaFxDynamicTexture3D/tree/master/out/artifacts/JavaFxDynamicTexture3D_jar) included in the checked in code that you can download.   On a Mac, just double click the `JavaFxDynamicTexture3D.jar` file and it should start (_see note below if this doesn't work_.)  
  
_Note: you may have to select the `JavaFxDynamicTexture3D.jar` file, then do a right click and select "Open" the first time you run the file to satisfy Mac OS' security checks._  You should also be able to run the JAR file on Windows or Linux systems, but you'll need to have a Java 8 JRE, or later installed and follow the appropriate process for each needed to run an executable JAR file.
### License
I'm publishing this source code under the MIT License (See: https://opensource.org/licenses/MIT)
## Credits
 - [IntelliJ IDEA from JetBrains](https://www.jetbrains.com/idea/) (my favorite development environment for Java coding. Thanks JetBrains!)
