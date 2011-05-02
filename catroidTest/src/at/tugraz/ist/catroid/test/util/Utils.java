package at.tugraz.ist.catroid.test.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import at.tugraz.ist.catroid.Consts;
import at.tugraz.ist.catroid.utils.UtilFile;

public class Utils {

	public static final int TYPE_IMAGE_FILE = 0;
	public static final int TYPE_SOUND_FILE = 1;

	/**
	 * saves a file into the project folder
	 * if project == null or "" file will be saved into Catroid folder
	 * 
	 * @param project
	 *            Folder where the file will be saved, this folder should exist
	 * @param name
	 *            Name of the file
	 * @param fileID
	 *            the id of the file --> needs the right context
	 * @param context
	 * @param type
	 *            type of the file: 0 = imagefile, 1 = soundfile
	 * @return the file
	 * @throws IOException
	 */
	public static File saveFileToProject(String project, String name, int fileID, Context context, int type)
			throws IOException {

		String filePath;
		if (project == null || project.equalsIgnoreCase("")) {
			filePath = Consts.DEFAULT_ROOT + "/" + name;
		} else {
			switch (type) {
				case 0:
					filePath = Consts.DEFAULT_ROOT + "/" + project + Consts.IMAGE_DIRECTORY + "/" + name;
					break;
				case 1:
					filePath = Consts.DEFAULT_ROOT + "/" + project + Consts.SOUND_DIRECTORY + "/" + name;
					break;
				default:
					filePath = Consts.DEFAULT_ROOT + "/" + name;
					break;
			}
		}
		//		BufferedInputStream in = new BufferedInputStream(context.getResources().openRawResource(fileID));
		//
		//		File file = new File(filePath);
		//		file.createNewFile();
		//
		//		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file), 1024);
		//		byte[] buffer = new byte[1024];
		//		int length = 0;
		//		while ((length = in.read(buffer)) > 0) {
		//			out.write(buffer, 0, length);
		//		}
		//
		//		in.close();
		//		out.flush();
		//		out.close();

		return createTestMediaFile(filePath, fileID, context);
	}

	public static boolean clearProject(String projectname) {
		File directory = new File(Consts.DEFAULT_ROOT + "/" + projectname);
		if (directory.exists()) {
			return UtilFile.deleteDirectory(directory);
		}
		return false;
	}

	public static File createTestMediaFile(String filePath, int fileID, Context context) throws IOException {

		File testImage = new File(filePath);

		if (!testImage.exists()) {
			testImage.createNewFile();
		}

		InputStream in = context.getResources().openRawResource(fileID);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(testImage));

		byte[] buffer = new byte[1024];
		int length = 0;

		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}

		in.close();
		out.flush();
		out.close();

		return testImage;
	}

}