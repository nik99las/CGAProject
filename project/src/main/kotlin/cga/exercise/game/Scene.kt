package cga.exercise.game

import cga.exercise.components.camera.TronCamera
import cga.exercise.components.geometry.Material
import cga.exercise.components.geometry.Mesh
import cga.exercise.components.geometry.Renderable
import cga.exercise.components.geometry.VertexAttribute
import cga.exercise.components.light.PointLight
import cga.exercise.components.light.SpotLight
import cga.exercise.components.shader.ShaderProgram
import cga.exercise.components.texture.Texture2D
import cga.framework.GLError
import cga.framework.GameWindow
import cga.framework.ModelLoader
import cga.framework.OBJLoader
import org.joml.Math
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15
import kotlin.math.sin


/**
 * Created by Fabian on 16.09.2017.
 */
class Scene(private val window: GameWindow)  {
    private val staticShader: ShaderProgram
  //  private val busShader: ShaderProgram



    var bodenr :Renderable
    var sternr1 :Renderable
    var sternr2 :Renderable
    var sternr3 :Renderable
    var sternr4 :Renderable
    var sternr5 :Renderable
    var sternr6 :Renderable
    var sternr7 :Renderable
    var sternr8 :Renderable
    var sternr9 :Renderable
    var sternr10 :Renderable
    var camera :TronCamera
    var material :Material
    var diff :Texture2D
    var emit :Texture2D
    var spec : Texture2D
    var starmaterial :Material
    var stardiff :Texture2D
    var staremit :Texture2D
    var starspec : Texture2D
    var car: Renderable
    var pointlight :PointLight
    var spotligt :SpotLight
    var olpx :Double
    var oldpy :Double
    var zugr :Renderable
    var haus :Renderable
    var haus2 :Renderable
    var haus3 :Renderable
    var haus4 :Renderable
    var haus5 :Renderable
    var haus6 :Renderable
    var haus7 :Renderable
    var haus8 :Renderable
    var haus9 :Renderable
    var haus10 :Renderable
    var haus11 :Renderable
    var haus12 :Renderable
    var haus13 :Renderable
    var zugdiff :Texture2D
    var zugemit :Texture2D
    var baummaterial :Material
    var zugspec :Texture2D



    //var pointlightstar :PointLight
    //var pointlightbus : PointLight
    

    //scene setup
    init {


        //staticShader = ShaderProgram("assets/shaders/simple_vert.glsl", "assets/shaders/simple_frag.glsl")
        staticShader = ShaderProgram("assets/shaders/tron_vert.glsl", "assets/shaders/tron_frag.glsl")
       // busShader = ShaderProgram("assets/shaders/bus_vert.glsl", "assets/shaders/bus_frag.glsl")

       //cycle  = ModelLoader.loadModel("assets/Light Cycle/Light Cycle/HQ_Movie cycle.obj",Math.toRadians(-90f),Math.toRadians(90f),0f) ?: throw IllegalArgumentException("Could not load the model")
        //cycle = ModelLoader.loadModel("assets/rx-7 veilside fortune.obj", Math.toRadians(0f), Math.toRadians(0f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        //cycle = ModelLoader.loadModel("assets/80-futuristic_car_2.0_cgtrader_obj/Futuristic_Car_2.1_obj.obj", Math.toRadians(0f), Math.toRadians(0f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        //cycle = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender/LowPolyCars2.obj", Math.toRadians(0f), Math.toRadians(-90f), 0f) ?: throw IllegalArgumentException("Could not load the model")
       // cycle = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender/LowPolyCars3.obj", Math.toRadians(0f), Math.toRadians(-90f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        //car = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender/LowPolyCars.obj", Math.toRadians(0f), Math.toRadians(-90f), 0f) ?: throw IllegalArgumentException("Could not load the model")
       // car.scaleLocal(Vector3f(2f,2f,2f))
        //car.rotateLocal(0f,0f,0f)
        //car.translateLocal(Vector3f(1.0f,0.0f,0.0f))



       // bus  = ModelLoader.loadModel("assets/bus/Material/bus_setia_negara_texturizer.obj",Math.toRadians(0f),Math.toRadians(0f),0f) ?: throw IllegalArgumentException("Could not load the model")
       // bus.scaleLocal(Vector3f(0.5f,0.5f,0.5f))
       // bus.translateLocal(Vector3f(-4.0f,0.0f,0.0f))

        //cycle = ModelLoader.loadModel("assets/E-45-Aircraft/E 45 Aircraft_obj.obj", Math.toRadians(0f), Math.toRadians(0f), 0f) ?: throw IllegalArgumentException("Could not load the model")

        val stride: Int = 8 * 4
        val attrPos = VertexAttribute(3, GL15.GL_FLOAT, stride, 0)
        val attrTC =  VertexAttribute(2, GL15.GL_FLOAT, stride, 3 * 4)
        val attrNorm =  VertexAttribute(3, GL15.GL_FLOAT, stride, 5 * 4)
        val vertexAttributes = arrayOf(attrPos, attrTC, attrNorm)

        val res2: OBJLoader.OBJResult = OBJLoader.loadOBJ("assets/models/ground.obj")
        val objMesh2: OBJLoader.OBJMesh = res2.objects[0].meshes[0]

        diff = Texture2D.invoke("assets/textures/ground_diff.png",true)
        diff.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        //emit = Texture2D.invoke("assets/textures/ground_emit.png",true)
        emit = Texture2D.invoke("assets/bus/Texturizer/Frame 2.png",true)
        emit.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        spec = Texture2D.invoke("assets/textures/ground_spec.png",true)
        spec.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)

        material = Material(diff,emit,spec,60f, Vector2f(64f,64f))


        val starres: OBJLoader.OBJResult = OBJLoader.loadOBJ("assets/star/stern.obj")
        val objMesh1: OBJLoader.OBJMesh = starres.objects[0].meshes[0]

        stardiff = Texture2D.invoke("assets/star/star.jpg",true)
        stardiff.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        staremit = Texture2D.invoke("assets/star/star.jpg",true)
        staremit.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        starspec = Texture2D.invoke("assets/star/star.jpg",true)
        starspec.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)

        starmaterial = Material(stardiff,staremit,starspec,60f, Vector2f(64f,64f))


        val zugres: OBJLoader.OBJResult = OBJLoader.loadOBJ("assets/bus/Sci_fi_Train.obj")
        val objMesh3: OBJLoader.OBJMesh = zugres.objects[0].meshes[0]

        zugdiff = Texture2D.invoke("assets/bus/engine/traindiffspec.png",true)
        zugdiff.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        zugemit = Texture2D.invoke("assets/bus/engine/train.png",true)
        zugemit.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        zugspec = Texture2D.invoke("assets/bus/engine/trainspec.png",true)
        zugspec.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)

        baummaterial = Material(zugdiff,zugemit,zugspec,60f, Vector2f(100f,100f))

        val baummesh  = Mesh(objMesh3.vertexData, objMesh3.indexData, vertexAttributes,baummaterial)


        zugr = Renderable(mutableListOf(baummesh))
        zugr.translateLocal(Vector3f(-20f,0f,-30f))
        zugr.rotateLocal(0f,35f,0f)




        val bodenmesh  = Mesh(objMesh2.vertexData, objMesh2.indexData, vertexAttributes,material)


        bodenr = Renderable(mutableListOf(bodenmesh))
        bodenr.translateLocal(Vector3f(-95f,0f,-195f))
        bodenr.rotateLocal(0f,90f,0f)
        bodenr.scaleLocal(Vector3f(10f,0.6f,0.9f))




        val sternmesh  = Mesh(objMesh1.vertexData, objMesh1.indexData, vertexAttributes,starmaterial)

        sternr1 = Renderable(mutableListOf(sternmesh))
        sternr1.translateLocal(Vector3f(-11f,1f,-20f))
        sternr1.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr1.rotateLocal(0f,0f,-25f)

        sternr2 = Renderable(mutableListOf(sternmesh))
        sternr2.translateLocal(Vector3f(-25f,1f,-40f))
        sternr2.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr2.rotateLocal(0f,0f,-25f)

        sternr3 = Renderable(mutableListOf(sternmesh))
        sternr3.translateLocal(Vector3f(-20f,1f,-60f))
        sternr3.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr3.rotateLocal(0f,0f,-25f)

        sternr4 = Renderable(mutableListOf(sternmesh))
        sternr4.translateLocal(Vector3f(-44f,1f,-80f))
        sternr4.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr4.rotateLocal(0f,0f,-25f)

        sternr5 = Renderable(mutableListOf(sternmesh))
        sternr5.translateLocal(Vector3f(-55f,1f,-100f))
        sternr5.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr5.rotateLocal(0f,0f,-25f)

        sternr6 = Renderable(mutableListOf(sternmesh))
        sternr6.translateLocal(Vector3f(-66f,1f,-120f))
        sternr6.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr6.rotateLocal(0f,0f,-25f)

        sternr7 = Renderable(mutableListOf(sternmesh))
        sternr7.translateLocal(Vector3f(-77f,1f,-140f))
        sternr7.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr7.rotateLocal(0f,0f,-25f)

        sternr8 = Renderable(mutableListOf(sternmesh))
        sternr8.translateLocal(Vector3f(-110f,1f,-220f))
        sternr8.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr8.rotateLocal(0f,0f,-25f)

        sternr9 = Renderable(mutableListOf(sternmesh))
        sternr9.translateLocal(Vector3f(-150f,1f,-290f))
        sternr9.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr9.rotateLocal(0f,0f,-25f)

        sternr10 = Renderable(mutableListOf(sternmesh))
        sternr10.translateLocal(Vector3f(-180f,1f,-380f))
        sternr10.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr10.rotateLocal(0f,0f,-25f)







       // car = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender/LowPolyCars2.obj", Math.toRadians(0f), Math.toRadians(-65f), 0f) ?: throw IllegalArgumentException("Could not load the model")

        car = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender/LowPolyCars.obj", Math.toRadians(0f), Math.toRadians(-63f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        car.scaleLocal(Vector3f(1.5f,1.5f,1.5f))
        car.translateLocal(Vector3f(-0.5f,0.2f,2.5f))


       // bus  = ModelLoader.loadModel("assets/bus/Sci_fi_Train.obj",Math.toRadians(0f),Math.toRadians(0f),0f) ?: throw IllegalArgumentException("Could not load the model")
        //bus.scaleLocal(Vector3f(0.5f,0.5f,0.5f))
       // bus.translateLocal(Vector3f(-4.0f,0.0f,0.0f))

        //Haus1--------links//
        haus = ModelLoader.loadModel("assets/house/CH_building1.obj", Math.toRadians(0f), Math.toRadians(115f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus.scaleLocal(Vector3f(0.4f,0.4f,0.4f))
        haus.translateLocal(Vector3f(-52f,0f,-20f))

        haus12 = ModelLoader.loadModel("assets/house/CH_building1.obj", Math.toRadians(0f), Math.toRadians(115f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus12.scaleLocal(Vector3f(0.4f,0.4f,0.4f))
        haus12.translateLocal(Vector3f(-143f,0f,-200f))

        haus13 = ModelLoader.loadModel("assets/house/CH_building1.obj", Math.toRadians(0f), Math.toRadians(115f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus13.scaleLocal(Vector3f(0.4f,0.4f,0.4f))
        haus13.translateLocal(Vector3f(-193f,0f,-300f))
        //Haus1------rechts//
        haus4 = ModelLoader.loadModel("assets/house/CH_building1.obj", Math.toRadians(0f), Math.toRadians(-65f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus4.scaleLocal(Vector3f(0.4f,0.4f,0.4f))
        haus4.translateLocal(Vector3f(25f,0f,-60f))

        haus6 = ModelLoader.loadModel("assets/house/CH_building1.obj", Math.toRadians(0f), Math.toRadians(-65f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus6.scaleLocal(Vector3f(0.4f,0.4f,0.4f))
        haus6.translateLocal(Vector3f(-20f,0f,-150f))

        haus7 = ModelLoader.loadModel("assets/house/CH_building1.obj", Math.toRadians(0f), Math.toRadians(-65f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus7.scaleLocal(Vector3f(0.4f,0.4f,0.4f))
        haus7.translateLocal(Vector3f(-45f,0f,-200f))


        //Haus2----------rechts//
        haus2 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(30f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus2.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus2.translateLocal(Vector3f(1700f,0f,-400f))

        haus3 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(30f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus3.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus3.translateLocal(Vector3f(1500f,0f,-800f))

        haus8 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(30f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus8.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus8.translateLocal(Vector3f(-10f,0f,-4000f))

        haus9 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(30f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus9.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus9.translateLocal(Vector3f(-3500f,0f,-11000f))
        //Haus2--------links//
        haus5 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(-155f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus5.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus5.translateLocal(Vector3f(-1400f,0f,-50f))

        haus10 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(-155f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus10.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus10.translateLocal(Vector3f(-2900f,0f,-3000f))

        haus11 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(-155f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus11.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus11.translateLocal(Vector3f(-3400f,0f,-4000f))

        //------------------------------------------------------//
        camera = TronCamera()
        //camera.rotateLocal(Math.toRadians(-35.0f),0f,0f)
        camera.rotateLocal(Math.toRadians(-10.0f),0.1f,0f)

        camera.translateLocal(Vector3f(4.5f,2.0f,4.0f))

        camera.parent = car

        pointlight = PointLight(Vector3f(0f,2f,0f),Vector3f(0f,0f,1f))
        pointlight.parent = car

        //pointlightbus = PointLight(Vector3f(0f,2f,5f),Vector3f(0f,0f,1f))
        //pointlightbus.parent = bus

        spotligt = SpotLight(Vector3f(1f,1f,-1f),Vector3f(1f,1f,1f),Math.cos(Math.toRadians(30f)),Math.cos(Math.toRadians(50f)))
        spotligt.rotateLocal(0f,-50f,0f)
        spotligt.parent = car



        olpx = window.mousePos.xpos
        oldpy = window.mousePos.ypos


        glClearColor(.00f, 0.5f, 0.5f, 0.0f); GLError.checkThrow()
        glEnable(GL_DEPTH_TEST); GLError.checkThrow()
        glDepthFunc(GL_LESS); GLError.checkThrow()


        glEnable(GL_CULL_FACE)
        glFrontFace(GL_CCW)
        glCullFace(GL_BACK)



    }

    fun render(dt: Float, t: Float) {

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        staticShader.use()
        camera.bind(staticShader)

        pointlight.bind(staticShader,"bike")
        pointlight.color = Vector3f(sin(1f*t),sin(1f*t+(2f/3f*Math.PI.toFloat())),sin(1f*t+(4f/3f*Math.PI.toFloat())))



       // pointlightbus.bind(busShader,"bus")
        //pointlightbus.color = Vector3f(sin(1f*t),sin(1f*t+(2f/3f*Math.PI.toFloat())),sin(1f*t+(4f/3f*Math.PI.toFloat())))

        spotligt.bind(staticShader,"bike", camera.getCalculateViewMatrix())
        staticShader.setUniform("shadingcolor",Vector3f(1f,1f,0f))


        bodenr.render(staticShader)

        //staticShader.setUniform("shadingcolor",Vector3f(1.0f,1.0f,0.0f))
        staticShader.setUniform("shadingcolor",Vector3f(1f,1f,0f))

        sternr1.render(staticShader)
        sternr2.render(staticShader)
        sternr3.render(staticShader)
        sternr4.render(staticShader)
        sternr5.render(staticShader)
        sternr6.render(staticShader)
        sternr7.render(staticShader)
        sternr8.render(staticShader)
        sternr9.render(staticShader)
        sternr10.render(staticShader)
        
        staticShader.setUniform("shadingcolor",Vector3f(1f,1f,1f))
        zugr.render(staticShader)
        haus.render(staticShader)
        haus2.render(staticShader)
        haus3.render(staticShader)
        haus4.render(staticShader)
        haus5.render(staticShader)
        haus6.render(staticShader)
        haus7.render(staticShader)
        haus8.render(staticShader)
        haus9.render(staticShader)
        haus10.render(staticShader)
        haus11.render(staticShader)
        haus12.render(staticShader)
        haus13.render(staticShader)
        staticShader.setUniform("shadingcolor",Vector3f(sin(1f*t),sin(1f*t+(2f/3f*Math.PI.toFloat())),sin(1f*t+(4f/3f*Math.PI.toFloat()))))
        car.render(staticShader)
        if(window.getKeyState(GLFW_KEY_E)){  // Um Bewegung anzuhalten E drücken

         car.translateLocal(Vector3f(0*dt,0.0f,0f*dt))

       }
        else {

            car.translateLocal(Vector3f(-7.5f * dt, 0.0f, -15f * dt))
        }

       //Thread.sleep(2000)
       // car.translateLocal(Vector3f(-7.5f * dt, 0.0f, -15f * dt))


    }

    fun update(dt: Float, t: Float) {

       // if(window.getKeyState(GLFW_KEY_W)){

           // car.translateLocal(Vector3f(-7.5f*dt,0.0f,-15f*dt))
      //  }
       // if(window.getKeyState(GLFW_KEY_S)){
           // car.translateLocal(Vector3f(7.5f*dt,0f,15.0f*dt))

       // }
        if(window.getKeyState(GLFW_KEY_A)){
            //car.translateLocal(Vector3f(-1.0f,0.0f,30.0f))
            car.translateLocal(Vector3f(-10.0f*dt,0.0f,5.0f*dt))
           // car.rotateLocal(0.0f,1f*dt,0.0f)
        }
        if(window.getKeyState(GLFW_KEY_D)){

            //car.translateLocal(Vector3f(1.0f,0.0f,-29.0f))
            car.translateLocal(Vector3f(10.0f*dt,0.0f,-5.0f*dt))
            //car.rotateLocal(0.0f,-1f*dt,0.0f)

        }

        if(car.getPosition() == Vector3f(-95f,0f,-195f))
            car.translateLocal(Vector3f(-0.5f,0.2f,2.5f))

    }

    fun onKey(key: Int, scancode: Int, action: Int, mode: Int) {}

    fun onMouseMove(xpos: Double, ypos: Double) {



        val distanceX = window.mousePos.xpos - olpx
        val distanceY = window.mousePos.ypos - oldpy

        if(distanceX > 0){
            camera.rotateAroundPoint(0f,Math.toRadians(distanceX.toFloat() *0.02f),0f, Vector3f(0f,0f,0f))
        }
        if(distanceX < 0){
           camera.rotateAroundPoint(0f,Math.toRadians(distanceX.toFloat() * 0.02f),0f, Vector3f(0f,0f,0f))
        }
        if(distanceY > 0){
            camera.rotateAroundPoint(Math.toRadians(distanceY.toFloat() *0.02f),0f,0f, Vector3f(0f,0f,0f))
        }
        if(distanceY < 0){
            camera.rotateAroundPoint(Math.toRadians(distanceY.toFloat() * 0.02f),0f,0f, Vector3f(0f,0f,0f))
        }

      olpx = window.mousePos.xpos
        oldpy = window.mousePos.ypos

    }


    fun cleanup() {}

}
