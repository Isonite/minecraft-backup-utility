import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

/**
 * Simple backup command-line utility for Minecraft save games.
 * 
 * @author Chandler Cain (chandler@clcain.com)
 * @version 2015.08.07
 *
 */
public class MinecraftBackupUtility
{
	/**
	 * Launch command-line interface.
	 * 
	 * @param args
	 *            command line arguments (ignored)
	 */
	public static void main(String[] args)
	{
		try
		{
			// OS check
			if (!SystemUtils.IS_OS_WINDOWS)
			{
				System.out.println(System.getProperty("os.name") + " not supported.\nApplication must exit.");
				System.exit(1);
			}

			System.out.println("Welcome to Minecraft Backup Utility.");

			String username = System.getProperty("user.name");
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			String minecraftDirPath = new String("C:/Users/" + username + "/AppData/Roaming/.minecraft");

			// check for minecraft installation
			File minecraftDirFile = new File(minecraftDirPath);
			if (!minecraftDirFile.exists())
			{
				System.out.println("\nMinecraft directory could not be found.\nApplication must exit.");
				System.exit(1);
			}

			String saveDirPath = new String(minecraftDirPath + "/saves");
			String backupDirPath = new String(minecraftDirPath + "/backup_utility/saves");
			File saveDir = new File(saveDirPath);
			File backupDir = new File(backupDirPath);
			String[] saves;
			String[] backups;
			String[] command;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			boolean validCommand = false;
			boolean validModuleCommand = false;

			while (true)
			{
				System.out.print("\nType the number of the action"
						+ " you would like to perform.\n1. Backup\n2. Restore\n3. Delete\n4. View\n:");
				command = scan.nextLine().split(" ");
				validCommand = false;
				validModuleCommand = false;

				// backup
				if (command.length == 1)
				{
					if (command[0].trim().equals("1"))
					{
						validCommand = true;
						saves = saveDir.list();
						if (saves != null && saves.length > 0)
						{
							System.out.println(
									"\nBackup initiated.\nType the number of the save you would like to back up.\n\nSaves available:");
							int i = 0;
							for (String save : saves)
							{
								System.out.println(++i + ". \"" + save + "\" "
										+ sdf.format(new File(saveDirPath + "/" + save).lastModified()));
							}
							System.out.print(":");

							command = scan.nextLine().split(" ");
							if (command.length == 1)
							{
								try
								{
									int choice = Integer.parseInt(command[0]);
									if (choice > 0 && choice <= i)
									{
										validModuleCommand = true;
										System.out.println("\nPerforming backup of \"" + saves[choice - 1] + "\".");
										File sourceFile = new File(saveDirPath + "/" + saves[choice - 1]);
										File destFile = new File(backupDirPath + "/" + saves[choice - 1]);
										if (destFile.exists())
										{
											FileUtils.forceDelete(destFile);
										}
										FileUtils.copyDirectory(sourceFile, destFile, false);
										System.out.println("Backup completed successfully.");
									}
								}
								catch (NumberFormatException e)
								{
									// do nothing
								}
							}
							if (!validModuleCommand)
							{
								System.out.println("\nThat is not a valid response. Exiting module.");
							}
						}
						else
						{
							System.out.println("\nCannot run backup. No saves were found.");
						}
					}

					// restore
					else if (command[0].trim().equals("2"))
					{
						validCommand = true;
						backups = backupDir.list();
						if (backups != null && backups.length > 0)
						{
							System.out.println(
									"\nRestore initiated.\nType the number of the backup you would like to restore.\n\nBackups available:");
							int i = 0;
							for (String backup : backups)
							{
								System.out.println(++i + ". \"" + backup + "\" "
										+ sdf.format(new File(backupDirPath + "/" + backup).lastModified()));
							}
							System.out.print(":");

							command = scan.nextLine().split(" ");
							if (command.length == 1)
							{
								try
								{
									int choice = Integer.parseInt(command[0]);
									if (choice > 0 && choice <= i)
									{
										validModuleCommand = true;
										System.out.println("\nPerforming restore of \"" + backups[choice - 1] + "\".");
										File sourceFile = new File(backupDirPath + "/" + backups[choice - 1]);
										File destFile = new File(saveDirPath + "/" + backups[choice - 1]);
										if (destFile.exists())
										{
											FileUtils.forceDelete(destFile);
										}
										FileUtils.copyDirectory(sourceFile, destFile, false);
										System.out.println("Restore completed successfully.");
									}
								}
								catch (NumberFormatException e)
								{
									// do nothing
								}
							}
							if (!validModuleCommand)
							{
								System.out.println("\nThat is not a valid response. Exiting module.");
							}
						}
						else
						{
							System.out.println("\nCannot run restore. No backups were found.");
						}
					}

					// delete
					else if (command[0].trim().equals("3"))
					{
						validCommand = true;
						backups = backupDir.list();
						if (backups != null && backups.length > 0)
						{
							System.out.println(
									"\nDelete initiated.\nType the number of the backup you would like to delete.\n\nBackups available:");
							int i = 0;
							for (String backup : backups)
							{
								System.out.println(++i + ". \"" + backup + "\" "
										+ sdf.format(new File(backupDirPath + "/" + backup).lastModified()));
							}
							System.out.print(":");

							command = scan.nextLine().split(" ");
							if (command.length == 1)
							{
								try
								{
									int choice = Integer.parseInt(command[0]);
									if (choice > 0 && choice <= i)
									{
										validModuleCommand = true;
										System.out.println("\nDeleting backup \"" + backups[choice - 1] + "\".");
										File destFile = new File(backupDirPath + "/" + backups[choice - 1]);
										FileUtils.forceDelete(destFile);
										System.out.println("Delete completed successfully.");
									}
								}
								catch (NumberFormatException e)
								{
									// do nothing
								}
							}
							if (!validModuleCommand)
							{
								System.out.println("\nThat is not a valid response. Exiting module.");
							}
						}
						else
						{
							System.out.println("\nCannot run delete. No backups were found.");
						}
					}

					// view
					else if (command[0].trim().equals("4"))
					{
						validCommand = true;
						System.out.println("\nShowing all saves and backups.\n\nCurrent saves:");
						saves = saveDir.list();
						if (saves != null && saves.length > 0)
						{
							for (String save : saves)
							{
								System.out.println(" - \"" + save + "\" "
										+ sdf.format(new File(saveDirPath + "/" + save).lastModified()));
							}
						}
						else
						{
							System.out.println("No saves found.");
						}

						System.out.println("\nCurrent backups:");
						backups = backupDir.list();
						if (backups != null && backups.length > 0)
						{
							for (String backup : backups)
							{
								System.out.println(" - \"" + backup + "\" "
										+ sdf.format(new File(backupDirPath + "/" + backup).lastModified()));
							}
						}
						else
						{
							System.out.println("No backups found.");
						}
					}
				}

				if (!validCommand)
				{
					System.out.println("\nThat is not a valid response. Please try again.");
				}
			}
		}
		catch (Exception e)
		{
			System.out
					.println("\nInternal error has occured. Application must exit.\nError message: " + e.getMessage());
			System.exit(1);
		}
	}
}
