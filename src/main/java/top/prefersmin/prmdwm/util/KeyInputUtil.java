package top.prefersmin.prmdwm.util;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyInputUtil {

    public static boolean isLeftCtrlPressed() {
        // 获取当前Minecraft实例的窗口句柄
        long window = Minecraft.getInstance().getWindow().getWindow();
        // 检查左Ctrl键是否被按下
        return GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS;
    }

}
