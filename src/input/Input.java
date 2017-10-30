package input;

import math.Vec2;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private static final int NUM_MOUSE_KEY_CODES = 512;
    private static final int NUM_MOUSE_BUTTON_CODES = 16;

    private static boolean[] keys = new boolean[NUM_MOUSE_KEY_CODES];
    private static boolean[] buttons = new boolean[NUM_MOUSE_BUTTON_CODES];

    private static ArrayList<Integer> keysPressed = new ArrayList<>();
    private static ArrayList<Integer> keysDown = new ArrayList<>();
    private static ArrayList<Integer> keysReleased = new ArrayList<>();

    private static ArrayList<Integer> buttonsPressed = new ArrayList<>();
    private static ArrayList<Integer> buttonsDown = new ArrayList<>();
    private static ArrayList<Integer> buttonsReleased = new ArrayList<>();

    public static Vec2 mouseScreenCoords = new Vec2();
    public static Vec2 mouseScreenCoordsChange = new Vec2();


    public static void init(long win) {
        glfwSetKeyCallback(win, (window, button, scanCode, action, mods) -> {
            if (action == GLFW_PRESS) {
                keys[button] = true;
            }
            if (action == GLFW_RELEASE) {
                keys[button] = false;
            }
        });
        glfwSetCursorPosCallback(win, (window, xPos, yPos) -> {

            mouseScreenCoordsChange = new Vec2((float)xPos, (float)yPos).sub(mouseScreenCoords);
            mouseScreenCoords = new Vec2((float) xPos, (float) yPos);
        });
        glfwSetMouseButtonCallback(win, (window, button, action, mods) -> {
            if (action == GLFW_PRESS) {
                buttons[button] = true;
            }
            if (action == GLFW_RELEASE) {
                buttons[button] = false;
            }
        });
    }

    public static void update() {
        checkForPressedDownReleased(keysPressed, keysDown, keysReleased, keys);
        checkForPressedDownReleased(buttonsPressed, buttonsDown, buttonsReleased, buttons);
        mouseScreenCoordsChange =new Vec2();
    }

    private static void checkForPressedDownReleased(
            ArrayList<Integer> pressed,
            ArrayList<Integer> down,
            ArrayList<Integer> released,
            boolean[] data) {

        released.clear();
        for (int i = 0; i < data.length; i++) {
            if (!data[i] && down.contains(i))
                released.add(i);
        }

        pressed.clear();
        for (int i = 0; i < data.length; i++) {
            if (data[i] && !down.contains(i))
                pressed.add(i);
        }

        down.clear();
        for (int i = 0; i < data.length; i++) {
            if (data[i])
                down.add(i);
        }
    }

    public static boolean isKeyPressed(int key) {
        return keysPressed.contains(key);
    }

    public static boolean isKeyDown(int key) {
        return keysDown.contains(key);
    }

    public static boolean isKeyReleased(int key) {
        return keysReleased.contains(key);
    }

    public static boolean isButtonPressed(int button) {
        return buttonsPressed.contains(button);
    }

    public static boolean isButtonDown(int button) {
        return buttonsDown.contains(button);
    }

    public static boolean isButtonReleased(int button) {
        return buttonsReleased.contains(button);
    }
}
