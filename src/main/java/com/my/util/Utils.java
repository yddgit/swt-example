package com.my.util;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Display;

/**
 * 工具类
 * 
 * @author yang.dongdong
 *
 */
public class Utils {

    /** The number of bytes in a kilobyte. */
    public static final long       ONE_KB    = 1024;
    /** The number of bytes in a kilobyte. */
    public static final BigInteger ONE_KB_BI = BigInteger.valueOf( ONE_KB );
    /** The number of bytes in a megabyte. */
    public static final long       ONE_MB    = ONE_KB * ONE_KB;
    /** The number of bytes in a megabyte. */
    public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply( ONE_KB_BI );
    /** The number of bytes in a gigabyte. */
    public static final long       ONE_GB    = ONE_KB * ONE_MB;
    /** The number of bytes in a gigabyte. */
    public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply( ONE_MB_BI );
    /** The number of bytes in a terabyte. */
    public static final long       ONE_TB    = ONE_KB * ONE_GB;
    /** The number of bytes in a terabyte. */
    public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply( ONE_GB_BI );
    /** The number of bytes in a petabyte. */
    public static final long       ONE_PB    = ONE_KB * ONE_TB;
    /** The number of bytes in a petabyte. */
    public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply( ONE_TB_BI );
    /** The number of bytes in an exabyte. */
    public static final long       ONE_EB    = ONE_KB * ONE_PB;
    /** The number of bytes in an exabyte. */
    public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply( ONE_PB_BI );
    /** The number of bytes in a zettabyte. */
    public static final BigInteger ONE_ZB    = BigInteger.valueOf( ONE_KB ).multiply( BigInteger.valueOf( ONE_EB ) );
    /** The number of bytes in a yottabyte. */
    public static final BigInteger ONE_YB    = ONE_KB_BI.multiply( ONE_ZB );

    private static ImageRegistry   imageRegistry;
    private static Clipboard       clipboard;

    public static URL newURL( String urlName ) {
        try {
            return new URL( urlName );
        } catch ( MalformedURLException e ) {
            throw new RuntimeException( "Malformed URL " + urlName, e );
        }
    }

    /**
     * 获取图像注册表
     * 
     * @return
     */
    public static ImageRegistry getImageRegistry() {
        if ( imageRegistry == null ) {
            imageRegistry = new ImageRegistry();
            imageRegistry.put( "folder", ImageDescriptor.createFromURL( newURL( "file:icons/folder.gif" ) ) );
            imageRegistry.put( "file", ImageDescriptor.createFromURL( newURL( "file:icons/file.gif" ) ) );
            imageRegistry.put( "run", ImageDescriptor.createFromURL( newURL( "file:icons/run.gif" ) ) );
            imageRegistry.put( "app1", ImageDescriptor.createFromURL( newURL( "file:icons/barapp1.gif" ) ) );
            imageRegistry.put( "app2", ImageDescriptor.createFromURL( newURL( "file:icons/barapp2.gif" ) ) );
            imageRegistry.put( "app3", ImageDescriptor.createFromURL( newURL( "file:icons/barapp3.gif" ) ) );
            imageRegistry.put( "app4", ImageDescriptor.createFromURL( newURL( "file:icons/barapp4.gif" ) ) );
            imageRegistry.put( "app5", ImageDescriptor.createFromURL( newURL( "file:icons/barapp5.gif" ) ) );
            imageRegistry.put( "app6", ImageDescriptor.createFromURL( newURL( "file:icons/barapp6.gif" ) ) );
        }
        return imageRegistry;
    }

    /**
     * 获取系统剪贴板
     * 
     * @return
     */
    public static Clipboard getClipboard() {
        if ( clipboard == null ) {
            clipboard = new Clipboard( Display.getCurrent() );
        }
        return clipboard;
    }

    /**
     * Returns a human-readable version of the file size, where the input represents a specific number of bytes.
     * <p>
     * If the size is over 1GB, the size is returned as the number of whole GB, i.e. the size is rounded down to the
     * nearest GB boundary.
     * </p>
     * <p>
     * Similarly for the 1MB and 1KB boundaries.
     * </p>
     * 
     * @param size the number of bytes
     * @return a human-readable display value (includes units - EB, PB, TB, GB, MB, KB or bytes)
     * @see <a href="https://issues.apache.org/jira/browse/IO-226">IO-226 - should the rounding be changed?</a>
     * @since 2.4
     */
    public static String byteCountToDisplaySize( long fileSize ) {
        BigInteger size = BigInteger.valueOf( fileSize );
        String displaySize;

        if ( size.divide( ONE_EB_BI ).compareTo( BigInteger.ZERO ) > 0 ) {
            displaySize = String.valueOf( size.divide( ONE_EB_BI ) ) + " EB";
        } else if ( size.divide( ONE_PB_BI ).compareTo( BigInteger.ZERO ) > 0 ) {
            displaySize = String.valueOf( size.divide( ONE_PB_BI ) ) + " PB";
        } else if ( size.divide( ONE_TB_BI ).compareTo( BigInteger.ZERO ) > 0 ) {
            displaySize = String.valueOf( size.divide( ONE_TB_BI ) ) + " TB";
        } else if ( size.divide( ONE_GB_BI ).compareTo( BigInteger.ZERO ) > 0 ) {
            displaySize = String.valueOf( size.divide( ONE_GB_BI ) ) + " GB";
        } else if ( size.divide( ONE_MB_BI ).compareTo( BigInteger.ZERO ) > 0 ) {
            displaySize = String.valueOf( size.divide( ONE_MB_BI ) ) + " MB";
        } else if ( size.divide( ONE_KB_BI ).compareTo( BigInteger.ZERO ) > 0 ) {
            displaySize = String.valueOf( size.divide( ONE_KB_BI ) ) + " KB";
        } else {
            displaySize = String.valueOf( size ) + " bytes";
        }
        return displaySize;
    }

}
