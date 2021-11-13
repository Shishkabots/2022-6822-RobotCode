// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public final static int DRIVETRAIN_RIGHT_FRONT_MOTOR = 1;
    public final static int DRIVETRAIN_RIGHT_BACK_MOTOR = 2;
    public final static int DRIVETRAIN_LEFT_FRONT_MOTOR = 3;
    public final static int DRIVETRAIN_LEFT_BACK_MOTOR = 4;
    
    
    public final static int DRIVER_STICK_PORT = 0;

    // Joystick Ids    
    public final static int JOYSTICK_LEFT_X = 0;
    public final static int JOYSTICK_LEFT_Y = 1;
    public final static int JOYSTICK_RIGHT_X = 2;
    public final static int JOYSTICK_RIGHT_Y = 3;
    
    public final static int JOYSTICK_BUTTON_X = 1;  
    public final static int JOYSTICK_BUTTON_A = 2;      
    public final static int JOYSTICK_BUTTON_B = 3;  
    public final static int JOYSTICK_BUTTON_Y = 4; 
    public final static int JOYSTICK_LEFTBUMPER = 5;  
    public final static int JOYSTICK_RIGHTBUMPER = 6;      
    public final static int JOYSTICK_LEFTTRIGGER = 7;  
    public final static int JOYSTICK_RIGHTTRIGGER = 8; 
    public final static int JOYSTICK_BUTTON_LEFTSTICK = 11;  
    public final static int JOYSTICK_BUTTON_RIGHTSTICK = 12;      

    // Speed sensitivity
    public final static double JOYSTICK_FULLSPEED = 1.0;
    public final static double JOYSTICK_THROTTLESPEED = 0.5;

    // Retention time in hours for logs on the RoboRIO target
    public final static double LOG_EXPIRATION_IN_HRS = 48;
    
}
