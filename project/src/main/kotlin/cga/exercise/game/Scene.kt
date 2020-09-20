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
import kotlin.system.exitProcess


/**
 * Created by Fabian on 16.09.2017.
 */
class Scene(private val window: GameWindow)  {
    private val staticShader: ShaderProgram

    var bodenr :Renderable
    var startbodenr :Renderable
    var zielbodenr :Renderable
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
    var cameraoben :TronCamera
    var material :Material
    var diff :Texture2D
    var emit :Texture2D
    var startemit :Texture2D
    var zielemit :Texture2D
    var spec : Texture2D
    var startmaterial :Material
    var zielmaterial :Material
    var starmaterial :Material
    var stardiff :Texture2D
    var staremit :Texture2D
    var starspec : Texture2D
    var car: Renderable
    var pointlight :PointLight
    var spotligt :SpotLight
    var olpx :Double
    var oldpy :Double
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
    var haus14 :Renderable
    var haus15 :Renderable
    var haus16 :Renderable
    var haus17 :Renderable
    var Rennauto :Renderable
    var Rennauto2 :Renderable
    var Rennauto3 :Renderable
    var Rennauto4 :Renderable
    var Rennauto5 :Renderable
    var toonShader : ShaderProgram
    var spielshader : ShaderProgram
    var gesammelteSterne :Int = 0
    var pointlightstern1 :PointLight
    var pointlightstern2 :PointLight
    var pointlightstern3 :PointLight
    var pointlightstern4 :PointLight
    var pointlightstern5 :PointLight
    var pointlightstern6 :PointLight
    var pointlightstern7 :PointLight
    var pointlightstern8 :PointLight
    var pointlightstern9 :PointLight
    var pointlightstern10 :PointLight
    var altePosition :Int = 0
    var sterni2 :Int = 0
    var sterni3 :Int = 0
    var sterni4 :Int = 0
    var sterni5 :Int = 0
    var sterni6 :Int = 0
    var sterni7 :Int = 0
    var sterni8 :Int = 0
    var sterni9 :Int = 0
    var sterni10 :Int = 0
    var flugzeug :Renderable

    //scene setup
    init {

        //staticShader = ShaderProgram("assets/shaders/simple_vert.glsl", "assets/shaders/simple_frag.glsl")
        staticShader = ShaderProgram("assets/shaders/tron_vert.glsl", "assets/shaders/tron_frag.glsl")
        toonShader = ShaderProgram("assets/shaders/toon_vert.glsl", "assets/shaders/toon_frag.glsl")

        spielshader= staticShader


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

        startemit = Texture2D.invoke("assets/bus/Texturizer/Start.png",true)
        startemit.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)

        zielemit = Texture2D.invoke("assets/bus/Texturizer/Ziel.png",true)
        zielemit.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)

        material = Material(diff,emit,spec,60f, Vector2f(64f,64f))
        startmaterial = Material(diff,startemit,spec,60f, Vector2f(64f,64f))
        zielmaterial = Material(diff,zielemit,spec,60f, Vector2f(64f,64f))

        val starres: OBJLoader.OBJResult = OBJLoader.loadOBJ("assets/star/stern.obj")
        val objMesh1: OBJLoader.OBJMesh = starres.objects[0].meshes[0]

        stardiff = Texture2D.invoke("assets/star/star.jpg",true)
        stardiff.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        staremit = Texture2D.invoke("assets/star/star.jpg",true)
        staremit.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        starspec = Texture2D.invoke("assets/star/star.jpg",true)
        starspec.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)

        starmaterial = Material(stardiff,staremit,starspec,60f, Vector2f(64f,64f))


        val bodenmesh  = Mesh(objMesh2.vertexData, objMesh2.indexData, vertexAttributes,material)
        val startbodenmesh  = Mesh(objMesh2.vertexData, objMesh2.indexData, vertexAttributes,startmaterial)
        val zielbodenmesh  = Mesh(objMesh2.vertexData, objMesh2.indexData, vertexAttributes,zielmaterial)


        bodenr = Renderable(mutableListOf(bodenmesh))
        bodenr.translateLocal(Vector3f(-95f,0f,-195f))
        bodenr.rotateLocal(0f,90f,0f)
        bodenr.scaleLocal(Vector3f(10f,0.6f,0.9f))

        startbodenr = Renderable(mutableListOf(startbodenmesh))
        startbodenr.translateLocal(Vector3f(10.3f,0f,15f))
        startbodenr.rotateLocal(0f,90f,0f)
        startbodenr.scaleLocal(Vector3f(0.5f,0.6f,0.9f))

       zielbodenr = Renderable(mutableListOf(zielbodenmesh))
        zielbodenr.translateLocal(Vector3f(-200.2f,0f,-405f))
        zielbodenr.rotateLocal(0f,90f,0f)
        zielbodenr.scaleLocal(Vector3f(0.5f,0.6f,0.9f))

        val sternmesh  = Mesh(objMesh1.vertexData, objMesh1.indexData, vertexAttributes,starmaterial)

        sternr1 = Renderable(mutableListOf(sternmesh))
        sternr1.translateLocal(Vector3f(-8f,1f,-22f))
        sternr1.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr1.rotateLocal(0f,-160f,-270f)

        sternr2 = Renderable(mutableListOf(sternmesh))
        sternr2.translateLocal(Vector3f(-19f,1f,-60f))
        sternr2.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr2.rotateLocal(0f,-160f,-270f)


        sternr3 = Renderable(mutableListOf(sternmesh))
        sternr3.translateLocal(Vector3f(-55f,1f,-100f))
        sternr3.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr3.rotateLocal(0f,-160f,-270f)


        sternr4 = Renderable(mutableListOf(sternmesh))
        sternr4.translateLocal(Vector3f(-67f,1f,-140f))
        sternr4.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr4.rotateLocal(0f,-160f,-270f)

        sternr5 = Renderable(mutableListOf(sternmesh))
        sternr5.translateLocal(Vector3f(-76.5f,1f,-175f))
        sternr5.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr5.rotateLocal(0f,-160f,-270f)

        sternr6 = Renderable(mutableListOf(sternmesh))
        sternr6.translateLocal(Vector3f(-107.5f,1f,-220f))
        sternr6.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr6.rotateLocal(0f,-160f,-270f)

        sternr7 = Renderable(mutableListOf(sternmesh))
        sternr7.translateLocal(Vector3f(-130.5f,1f,-250f))
        sternr7.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr7.rotateLocal(0f,-160f,-270f)

         sternr8 = Renderable(mutableListOf(sternmesh))
         sternr8.translateLocal(Vector3f(-139.5f,1f,-300f))
         sternr8.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
         sternr8.rotateLocal(0f,-160f,-270f)


        sternr9 = Renderable(mutableListOf(sternmesh))
        sternr9.translateLocal(Vector3f(-168f,1f,-340f))
        sternr9.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr9.rotateLocal(0f,-160f,-270f)

        sternr10 = Renderable(mutableListOf(sternmesh))
        sternr10.translateLocal(Vector3f(-196f,1f,-380f))
        sternr10.scaleLocal(Vector3f(0.2f,0.2f,0.2f))
        sternr10.rotateLocal(0f,-160f,-270f)

        flugzeug = ModelLoader.loadModel("assets/Beriev_A50/BerievA50.obj",Math.toRadians(0f), Math.toRadians(-90f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        flugzeug.scaleLocal(Vector3f(0.1f,0.1f,0.1f))
        flugzeug.translateLocal(Vector3f(20f,300f, 0f))
        flugzeug.rotateLocal(0f,-45f,0f)

        //Zweites Auto Gegner
        Rennauto = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender2/LowPolyCars.obj", Math.toRadians(0f), Math.toRadians(-90f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        Rennauto.scaleLocal(Vector3f(2.0f,2.0f,2.0f))
        Rennauto.translateLocal(Vector3f(-17.5f,0f,-30f))
        Rennauto.rotateLocal(0f,35f,0f)

        Rennauto2 = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender2/LowPolyCars.obj", Math.toRadians(0f), Math.toRadians(-90f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        Rennauto2.scaleLocal(Vector3f(2.0f,2.0f,2.0f))
        Rennauto2.translateLocal(Vector3f(-25f,0f,-60f))
        Rennauto2.rotateLocal(0f,35f,0f)

        Rennauto3 = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender2/LowPolyCars.obj", Math.toRadians(0f), Math.toRadians(-90f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        Rennauto3.scaleLocal(Vector3f(2.0f,2.0f,2.0f))
        Rennauto3.translateLocal(Vector3f(-42.5f,0f,-80f))
        Rennauto3.rotateLocal(0f,35f,0f)

        Rennauto4 = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender2/LowPolyCars.obj", Math.toRadians(0f), Math.toRadians(-90f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        Rennauto4.scaleLocal(Vector3f(2.0f,2.0f,2.0f))
        Rennauto4.translateLocal(Vector3f(-59.5f,0f,-122f))
        Rennauto4.rotateLocal(0f,35f,0f)

        Rennauto5 = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender2/LowPolyCars.obj", Math.toRadians(0f), Math.toRadians(-90f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        Rennauto5.scaleLocal(Vector3f(2.0f,2.0f,2.0f))
        Rennauto5.translateLocal(Vector3f(-78.5f,0f,-150f))
        Rennauto5.rotateLocal(0f,35f,0f)

        car = ModelLoader.loadModel("assets/Low Poly Cars (Free)_blender/LowPolyCars.obj", Math.toRadians(0f), Math.toRadians(-63f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        car.scaleLocal(Vector3f(1.5f,1.5f,1.5f))
        car.translateLocal(Vector3f(6f,0.2f,15f))


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

        haus16 = ModelLoader.loadModel("assets/house/CH_building1.obj", Math.toRadians(0f), Math.toRadians(115f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus16.scaleLocal(Vector3f(0.4f,0.4f,0.4f))
        haus16.translateLocal(Vector3f(-295f,0f,-500f))

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

        haus14 = ModelLoader.loadModel("assets/house/CH_building1.obj", Math.toRadians(0f), Math.toRadians(-65f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus14.scaleLocal(Vector3f(0.4f,0.4f,0.4f))
        haus14.translateLocal(Vector3f(-193f,0f,-500f))

        haus17 = ModelLoader.loadModel("assets/house/CH_building1.obj", Math.toRadians(0f), Math.toRadians(-65f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus17.scaleLocal(Vector3f(0.4f,0.4f,0.4f))
        haus17.translateLocal(Vector3f(-420f,0f,-950f))


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

        haus15 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(30f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus15.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus15.translateLocal(Vector3f(-14550f,0f,-33000f))

        haus15 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(30f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus15.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus15.translateLocal(Vector3f(-10570f,0f,-25000f))

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

        haus11 = ModelLoader.loadModel("assets/NewYorkHouse/13940_New_York_City_Brownstone_Building_v1_l2.obj", Math.toRadians(-90f), Math.toRadians(-155f), 0f) ?: throw IllegalArgumentException("Could not load the model")
        haus11.scaleLocal(Vector3f(0.01f,0.01f,0.01f))
        haus11.translateLocal(Vector3f(-15500f,0f,-28000f))

        //------------------------------------------------------//
        camera = TronCamera()
        cameraoben = TronCamera()


        camera.rotateLocal(Math.toRadians(-10.0f),0.4f,0.2f)
        camera.translateLocal(Vector3f(4.5f,2.0f,4.0f))
        camera.parent = car

        cameraoben.rotateLocal(Math.toRadians(-80.0f),0.0f,0.0f)
        cameraoben.translateLocal(Vector3f(0.0f,12.0f,15.0f))

        cameraoben.parent = car

        pointlight = PointLight(Vector3f(0f,2f,0f),Vector3f(0f,0f,1f))
        pointlight.parent = car

        pointlightstern1 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern1.parent = sternr1

        pointlightstern2 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern2.parent = sternr2

        pointlightstern3 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern3.parent = sternr3

        pointlightstern4 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern4.parent = sternr4

        pointlightstern5 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern5.parent = sternr5

        pointlightstern6 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern6.parent = sternr6

        pointlightstern7 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern7.parent = sternr7

        pointlightstern8 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern8.parent = sternr8

        pointlightstern9 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern9.parent = sternr9

        pointlightstern10 = PointLight(Vector3f(0f,3f,0f),Vector3f(0f,0f,1f))
        pointlightstern10.parent = sternr10



        spotligt = SpotLight(Vector3f(1f,1f,-1f),Vector3f(0f,0f,0f),Math.cos(Math.toRadians(30f)),Math.cos(Math.toRadians(50f)))
        spotligt.rotateLocal(0f,-50f,0f)
        spotligt.parent = car


        olpx = window.mousePos.xpos
        oldpy = window.mousePos.ypos


        glClearColor(.00f, 0.5f, 0.9f, 0.0f); GLError.checkThrow()
        glEnable(GL_DEPTH_TEST); GLError.checkThrow()
        glDepthFunc(GL_LESS); GLError.checkThrow()


        glEnable(GL_CULL_FACE)
        glFrontFace(GL_CCW)
        glCullFace(GL_BACK)





    }

    fun render(dt: Float, t: Float) {

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        //Shaderauswahl
        if(window.getKeyState(GLFW_KEY_J)) { //J für Toon Shader
           spielshader= toonShader

        }
        if(window.getKeyState(GLFW_KEY_H)) { //H für Static Shader
            spielshader= staticShader

        }

        spielshader.use()
        pointlight.bind(spielshader, "bike")
        pointlight.color = Vector3f(1f,1f,1f)

        pointlightstern1.bind(spielshader, "stern1")
        pointlightstern1.color = Vector3f(1f,1f,0f)

        pointlightstern2.bind(spielshader, "stern2")
        pointlightstern2.color = Vector3f(1f,1f,0f)

        pointlightstern3.bind(spielshader, "stern3")
        pointlightstern3.color = Vector3f(1f,1f,0f)

        pointlightstern4.bind(spielshader, "stern4")
        pointlightstern4.color = Vector3f(1f,1f,0f)

        pointlightstern5.bind(spielshader, "stern5")
        pointlightstern5.color = Vector3f(1f,1f,0f)

        pointlightstern6.bind(spielshader, "stern6")
        pointlightstern6.color = Vector3f(1f,1f,0f)

        pointlightstern7.bind(spielshader, "stern7")
        pointlightstern7.color = Vector3f(1f,1f,0f)

        pointlightstern8.bind(spielshader, "stern8")
        pointlightstern8.color = Vector3f(1f,1f,0f)

        pointlightstern9.bind(spielshader, "stern9")
        pointlightstern9.color = Vector3f(1f,1f,0f)

        pointlightstern10.bind(spielshader, "stern10")
        pointlightstern10.color = Vector3f(1f,1f,0f)



        if(window.getKeyState(GLFW_KEY_K)){  // K für Kamera drücken

            cameraoben.bind(spielshader)

        }
        else {

            camera.bind(spielshader)
        }


        spotligt.bind(spielshader,"bike", camera.getCalculateViewMatrix())
        spielshader.setUniform("shadingcolor",Vector3f(1f,1f,1f))


        bodenr.render(spielshader)

        startbodenr.render(spielshader)
        zielbodenr.render(spielshader)

        flugzeug.render(spielshader)
        flugzeug.translateLocal(Vector3f(-450f * dt, 0.0f, 0f * dt))


        spielshader.setUniform("shadingcolor",Vector3f(1f,1f,0f))

        sternr1.render(spielshader)
        sternr2.render(spielshader)
        sternr3.render(spielshader)
        sternr4.render(spielshader)
        sternr5.render(spielshader)
        sternr6.render(spielshader)
        sternr7.render(spielshader)
        sternr8.render(spielshader)
        sternr9.render(spielshader)
        sternr10.render(spielshader)

        spielshader.setUniform("shadingcolor",Vector3f(1f,1f,1f))
        haus.render(spielshader)
        haus2.render(spielshader)
        haus3.render(spielshader)
        haus4.render(spielshader)
        haus5.render(spielshader)
        haus6.render(spielshader)
        haus7.render(spielshader)
        haus8.render(spielshader)
        haus9.render(spielshader)
        haus10.render(spielshader)
        haus11.render(spielshader)
        haus12.render(spielshader)
        haus13.render(spielshader)
        haus14.render(spielshader)
        haus15.render(spielshader)
        haus16.render(spielshader)
        haus17.render(spielshader)
        car.render(spielshader)
        Rennauto.render(spielshader)
        Rennauto2.render(spielshader)
        Rennauto3.render(spielshader)
        Rennauto4.render(spielshader)
        Rennauto5.render(spielshader)

        if(window.getKeyState(GLFW_KEY_E)){  // Um Bewegung anzuhalten E drücken
         car.translateLocal(Vector3f(0*dt,0.0f,0f*dt))
       }
        else  {
            car.translateLocal(Vector3f(-15f * dt, 0.0f, -30f * dt))
        }




       // println(car.getPosition())
       // println(gesammelteSterne)

    }

    fun update(dt: Float, t: Float) {



      /*  if (window.getKeyState(GLFW_KEY_W)) {

            car.translateLocal(Vector3f(-15f * dt, 0.0f, -30f * dt))
        }
        if (window.getKeyState(GLFW_KEY_S)) {
            car.translateLocal(Vector3f(7.5f * dt, 0f, 15.0f * dt))

        }

       */
        if (window.getKeyState(GLFW_KEY_A)) {
            //car.translateLocal(Vector3f(-1.0f,0.0f,30.0f))
            car.translateLocal(Vector3f(-10.0f * dt, 0.0f, 5.0f * dt))
            // car.rotateLocal(0.0f,1f*dt,0.0f)
        }
        if (window.getKeyState(GLFW_KEY_D)) {

            //car.translateLocal(Vector3f(1.0f,0.0f,-29.0f))
            car.translateLocal(Vector3f(10.0f * dt, 0.0f, -5.0f * dt))
            //car.rotateLocal(0.0f,-1f*dt,0.0f)

        }

        //Collision mit Rennautos
        if (car.getPosition().x <= -36f && car.getPosition().x >= -37f && car.getPosition().z <= -51f && car.getPosition().z >= -55f) {
            println("Du hast verloren")
            exitProcess(0)


        }
        if (car.getPosition().x <= -50f && car.getPosition().x >= -52f && car.getPosition().z <= -110f && car.getPosition().z >= -115f) {
            println("Du hast verloren")
            exitProcess(0)


        }
        if (car.getPosition().x <= -85.2f && car.getPosition().x >= -85.8f && car.getPosition().z <= -150f && car.getPosition().z >= -151f) {
            println("Du hast verloren")
            exitProcess(0)


        }
        if (car.getPosition().x <= -120f && car.getPosition().x >= -121f && car.getPosition().z <= -235f && car.getPosition().z >= -236f) {
            println("Du hast verloren")
            exitProcess(0)


        }
        if (car.getPosition().x <= -158.4f && car.getPosition().x >= -159f && car.getPosition().z <= -290f && car.getPosition().z >= -294f) {
            println("Du hast verloren")
            exitProcess(0)


        }

        // Linke Spur Ende
        if (car.getPosition().x <= -210.7f && car.getPosition().x >= -212.3f) {
            println("Du hast gewonnen und $gesammelteSterne/10 Sterne gesammelt")
            exitProcess(0)
        }

        // Mitlere Spur Ende
         if (car.getPosition().x <= -204.3f && car.getPosition().x >= -205.9f && car.getPosition().z <= -405.1f && car.getPosition().z >= -405.9) {
              println("Du hast gewonnen und $gesammelteSterne/10 Sterne gesammelt")
             exitProcess(0)
         }

        //Rechte Spur Ende
        if (car.getPosition().x <= -197.8f && car.getPosition().x >= -199.7f && car.getPosition().z <= -408.2f && car.getPosition().z >= -409.2) {
           println("Du hast gewonnen und $gesammelteSterne/10 Sterne gesammelt")
           exitProcess(0)
             }

    if (car.getPosition().x <= -11.1f && car.getPosition().x >= -11.92f && car.getPosition().z <= -18.0f && car.getPosition().z >= -19.8f && altePosition == 0 ) {

        sternr1.translateLocal(Vector3f(10000f, 10000f, 10000f))
        gesammelteSterne += 1
        altePosition = 1
    }


      if(car.getPosition().x <= -22.4f &&  car.getPosition().x >= -23.3f  && car.getPosition().z <= -55.9f &&  car.getPosition().z >= -57.3f && sterni2 == 0)
        {

            sternr2.translateLocal(Vector3f(10000f,10000f,10000f))
            gesammelteSterne += 1
            sterni2= 1
        }

        if(car.getPosition().x <= -58.4f &&  car.getPosition().x >= -59.2f  && car.getPosition().z <= -95.5f &&  car.getPosition().z >= -98.6f && sterni3 == 0)
        {

            sternr3.translateLocal(Vector3f(10000f,10000f,10000f))
            gesammelteSterne += 1
            sterni3 = 1
        }
        if(car.getPosition().x <= -69.8f &&  car.getPosition().x >= -71.2f  && car.getPosition().z <= -136.8f &&  car.getPosition().z >= -137.3f && sterni4 == 0)
        {

            sternr4.translateLocal(Vector3f(10000f,10000f,10000f))
            gesammelteSterne += 1
            sterni4 == 1
        }

        if(car.getPosition().x <= -79.0f &&  car.getPosition().x >= -80.2f  && car.getPosition().z <= -170.6f &&  car.getPosition().z >= -171.9f && sterni5 == 0)
        {

            sternr5.translateLocal(Vector3f(10000f,10000f,10000f))
            gesammelteSterne += 1
            sterni5 = 1
        }
        if(car.getPosition().x <= -110.0f &&  car.getPosition().x >= -111.9f  && car.getPosition().z <= -216f &&  car.getPosition().z >= -217.8f && sterni6 == 0)
        {

            sternr6.translateLocal(Vector3f(10000f,10000f,10000f))
            gesammelteSterne += 1
            sterni6 = 1
        }
        if(car.getPosition().x <= -133.4f &&  car.getPosition().x >= -134f  && car.getPosition().z <= -245.6f &&  car.getPosition().z >= -247.5f && sterni7 == 0)
        {

            sternr7.translateLocal(Vector3f(10000f,10000f,10000f))
            gesammelteSterne += 1
            sterni7 = 1
        }
        if(car.getPosition().x <= -141.5f &&  car.getPosition().x >= -143.8f  && car.getPosition().z <= -296f &&  car.getPosition().z >= -297.2f && sterni8 == 0)
        {

            sternr8.translateLocal(Vector3f(10000f,10000f,10000f))
            gesammelteSterne += 1
            sterni8 = 1
        }
        if(car.getPosition().x <= -170.5f &&  car.getPosition().x >= -172.8f  && car.getPosition().z <= -336.0f &&  car.getPosition().z >= -337.9f && sterni9 == 0)
        {

            sternr9.translateLocal(Vector3f(10000f,10000f,10000f))
            gesammelteSterne += 1
            sterni9 = 1
        }
        if(car.getPosition().x <= -198.6f &&  car.getPosition().x >= -200.4f  && car.getPosition().z <= -376f &&  car.getPosition().z >= -377.5f && sterni10 == 0 )
        {

            sternr10.translateLocal(Vector3f(10000f,10000f,10000f))
            gesammelteSterne += 1
            sterni10 = 1
        }
    }

    fun onKey(key: Int, scancode: Int, action: Int, mode: Int) {

    }

    fun onMouseMove(xpos: Double, ypos: Double) {



        val distanceX = window.mousePos.xpos - olpx
        val distanceY = window.mousePos.ypos - oldpy

        if(distanceX > 0){
            camera.rotateAroundPoint(0f,Math.toRadians(distanceX.toFloat() *0.02f),0f, Vector3f(0f,0f,0f))
            cameraoben.rotateAroundPoint(0f,Math.toRadians(distanceX.toFloat() *0.02f),0f, Vector3f(0f,0f,0f))
        }
        if(distanceX < 0){
            camera.rotateAroundPoint(0f,Math.toRadians(distanceX.toFloat() * 0.02f),0f, Vector3f(0f,0f,0f))
            cameraoben.rotateAroundPoint(0f,Math.toRadians(distanceX.toFloat() * 0.02f),0f, Vector3f(0f,0f,0f))
        }
        if(distanceY > 0){
            camera.rotateAroundPoint(Math.toRadians(distanceY.toFloat() *0.02f),0f,0f, Vector3f(0f,0f,0f))
            cameraoben.rotateAroundPoint(Math.toRadians(distanceY.toFloat() *0.02f),0f,0f, Vector3f(0f,0f,0f))
        }
        if(distanceY < 0){
            camera.rotateAroundPoint(Math.toRadians(distanceY.toFloat() * 0.02f),0f,0f, Vector3f(0f,0f,0f))
            cameraoben.rotateAroundPoint(Math.toRadians(distanceY.toFloat() * 0.02f),0f,0f, Vector3f(0f,0f,0f))
        }

        olpx = window.mousePos.xpos
        oldpy = window.mousePos.ypos

    }


    fun cleanup() {

    }

}
