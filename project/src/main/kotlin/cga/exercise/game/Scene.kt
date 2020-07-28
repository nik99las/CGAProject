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
import cga.framework.GameWindow.MousePosition
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



    var bodenr :Renderable
    var camera :TronCamera
    var material :Material
    var diff :Texture2D
    var emit :Texture2D
    var spec : Texture2D
    var cycle: Renderable
    var pointlight :PointLight
    var spotligt :SpotLight
    var olpx :Double
    var oldpy :Double
    var bus :Renderable






    //scene setup
    init {


        //staticShader = ShaderProgram("assets/shaders/simple_vert.glsl", "assets/shaders/simple_frag.glsl")
        staticShader = ShaderProgram("assets/shaders/tron_vert.glsl", "assets/shaders/tron_frag.glsl")


        //cycle  = ModelLoader.loadModel("assets/Light Cycle/Light Cycle/HQ_Movie cycle.obj",Math.toRadians(-90f),Math.toRadians(90f),0f) ?: throw IllegalArgumentException("Could not load the model")
        cycle = ModelLoader.loadModel("assets/rx-7 veilside fortune.obj", Math.toRadians(0f), Math.toRadians(0f), 0f) ?: throw IllegalArgumentException("Could not load the model")

        cycle.scaleLocal(Vector3f(2f,2f,2f))


        bus  = ModelLoader.loadModel("assets/bus/Material/bus_setia_negara_texturizer.obj",Math.toRadians(0f),Math.toRadians(0f),0f) ?: throw IllegalArgumentException("Could not load the model")
        bus.scaleLocal(Vector3f(0.5f,0.5f,0.5f))

        //cycle = ModelLoader.loadModel("assets/E-45-Aircraft/E 45 Aircraft_obj.obj", Math.toRadians(0f), Math.toRadians(0f), 0f) ?: throw IllegalArgumentException("Could not load the model")



        val res2: OBJLoader.OBJResult = OBJLoader.loadOBJ("assets/models/ground.obj")
        val objMesh2: OBJLoader.OBJMesh = res2.objects[0].meshes[0]

        diff = Texture2D.invoke("assets/textures/ground_diff.png",true)
        diff.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        //emit = Texture2D.invoke("assets/textures/ground_emit.png",true)
        emit = Texture2D.invoke("assets/bus/Texturizer/vier.jpg",true)
        emit.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        spec = Texture2D.invoke("assets/textures/ground_spec.png",true)
        spec.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)


       material = Material(diff,emit,spec,60f, Vector2f(64f,64f))

        val stride: Int = 8 * 4
        val attrPos = VertexAttribute(3, GL15.GL_FLOAT, stride, 0)
        val attrTC =  VertexAttribute(2, GL15.GL_FLOAT, stride, 3 * 4)
        val attrNorm =  VertexAttribute(3, GL15.GL_FLOAT, stride, 5 * 4)
        val vertexAttributes = arrayOf(attrPos, attrTC, attrNorm)
        //kugelMesh = Mesh(objMesh.vertexData, objMesh.indexData, vertexAttributes)
        val bodenmesh  = Mesh(objMesh2.vertexData, objMesh2.indexData, vertexAttributes,material)


        bodenr = Renderable(mutableListOf(bodenmesh))
        bodenr.rotateLocal(0f,90f,0f)
        bodenr.scaleLocal(Vector3f(5f,0.6f,0.5f))

        //kugelr = Renderable(mutableListOf(kugelMesh))



        //bodenr.rotateLocal(Math.toRadians(90.0f),0f,0f)
        //bodenr.scaleLocal(Vector3f(0.03f,0.03f,0.03f))
        // kugelr.scaleLocal(Vector3f(0.5f,0.5f,0.5f))


        camera = TronCamera()
        camera.rotateLocal(Math.toRadians(-35.0f),0f,0f)
        camera.translateLocal(Vector3f(0.0f,0.0f,4.0f))
        camera.parent = cycle


        pointlight = PointLight(Vector3f(0f,2f,0f),Vector3f(0f,0f,1f))
        pointlight.parent = cycle


        spotligt = SpotLight(Vector3f(0f,0.5f,0f),Vector3f(1f,1f,1f),Math.cos(Math.toRadians(30f)),Math.cos(Math.toRadians(50f)))
        spotligt.parent = cycle


        olpx = window.mousePos.xpos
        oldpy = window.mousePos.ypos


        glClearColor(0.0f, 0.0f, 0.0f, 1.0f); GLError.checkThrow()
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

       // pointlight.bind(staticShader,"bus")
       // pointlight.color = Vector3f(sin(1f*t),sin(1f*t+(2f/3f*Math.PI.toFloat())),sin(1f*t+(4f/3f*Math.PI.toFloat())))

        spotligt.bind(staticShader,"bike", camera.getCalculateViewMatrix())
        staticShader.setUniform("shadingcolor",Vector3f(1f,1f,0f))

        bodenr.render(staticShader)
        staticShader.setUniform("shadingcolor",Vector3f(sin(1f*t),sin(1f*t+(2f/3f*Math.PI.toFloat())),sin(1f*t+(4f/3f*Math.PI.toFloat()))))

        cycle.render(staticShader)
        bus.render(staticShader)




    }

    fun update(dt: Float, t: Float) {

        if(window.getKeyState(GLFW_KEY_W)){

            cycle.translateLocal(Vector3f(0.0f,0.0f,-5.0f*dt))
        }
        if(window.getKeyState(GLFW_KEY_S)){
            cycle.translateLocal(Vector3f(0f,0f,5.0f*dt))

        }
        if(window.getKeyState(GLFW_KEY_A)){
            cycle.translateLocal(Vector3f(0.0f,0.0f,-5.0f*dt))
            cycle.rotateLocal(0.0f,1f*dt,0.0f)
        }
        if(window.getKeyState(GLFW_KEY_D)){

            cycle.translateLocal(Vector3f(0.0f,0.0f,-5.0f*dt))
            cycle.rotateLocal(0.0f,-1f*dt,0.0f)

        }


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
