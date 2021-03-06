package org.walkmod;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class WalkmodDispatcherTest extends AbstractWalkmodExecutionTest{

	@Test
	public void testNoArgs() throws Exception {
		Assert.assertTrue(run(null).contains("walkmod COMMAND [arg...]"));
	}

	@Test
	public void testUsage() throws Exception {
		Assert.assertTrue(run(new String[] { "--help" }).contains("walkmod COMMAND [arg...]"));
	}

	@Test
	public void testApply() throws Exception {
		String aux = run(new String[] { "apply", "-e" });
		System.out.println(aux);
		Assert.assertTrue(aux.contains("TRANSFORMATION CHAIN SUCCESS"));
	}

	@Test
	public void testApplyWithParams() throws Exception {
		Assert.assertTrue(run(
				new String[] { "apply", "--includes",
						new File("src/main/java/org/walkmod/WalkmodFacade.java").getAbsolutePath() }).contains(
				"TRANSFORMATION CHAIN SUCCESS"));
	}

	@Test
	public void testInstall() throws Exception {
		Assert.assertTrue(run(new String[] { "install" }).contains("PLUGIN INSTALLATION COMPLETE"));

	}

	@Test
	public void testCheck() throws Exception {
		Assert.assertTrue(run(new String[] { "check" }).contains("TRANSFORMATION CHAIN SUCCESS"));

	}

	@Test
	public void testCheckWithParams() throws Exception {
		Assert.assertTrue(run(
				new String[] { "check", "--includes",
						new File("src/main/java/org/walkmod/WalkmodFacade.java").getAbsolutePath() }).contains(
				"TRANSFORMATION CHAIN SUCCESS"));
	}

	@Test
	public void testVersion() throws Exception {
		Assert.assertTrue(run(new String[] { "--version" }).contains("Walkmod version"));
	}

	@Test
	public void testInvalidGoal() throws Exception {
		Assert.assertTrue(run(new String[] { "foo" }).contains("Expected a command, got foo"));
	}

	@Test
	public void testApplyWithInvalidArgs() throws Exception {
		Assert.assertTrue(run(new String[] { "apply", "-F" }).contains("Unknown option: -F"));
	}

	@Test
	public void testPrintPlugins() throws Exception {
		Assert.assertTrue(run(new String[] { "plugins" }).contains("javalang"));
	}

	@Test
	public void testInitWithExistingCfgFile() throws Exception {
		Assert.assertTrue(run(new String[] { "init", "-f", "xml" }).contains("already exists"));
	}

	@Test
	public void testInitWithNonExistingCfgFile() throws Exception {

		File tmp = new File("src/test/resources/initTest");
		tmp.mkdirs();
		if (tmp.exists()) {
			String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
			System.setProperty("user.dir", tmp.getAbsolutePath());
			try {
				Assert.assertTrue(run(new String[] { "init", "-f", "xml" }).contains("walkmod.xml] CREATION COMPLETE"));
			} finally {
				System.setProperty("user.dir", userDir);
				FileUtils.deleteDirectory(tmp);
			}

		}

	}

	@Test
	public void testAddPlugin() throws Exception {
		File tmp = new File("src/test/resources/initTestAddPlugin");
		tmp.mkdirs();
		if (tmp.exists()) {
			String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
			System.setProperty("user.dir", tmp.getAbsolutePath());
			try {
				run(new String[] { "add-plugin", "org.walkmod:walkmod-imports-cleaner-plugin:2.0" });
			} catch (Exception e) {
				e.printStackTrace();
				Assert.assertFalse(false);
			} finally {
				System.setProperty("user.dir", userDir);

				File cfg = new File(tmp, "walkmod.xml");
				Assert.assertTrue(cfg.exists());

				String content = FileUtils.readFileToString(cfg);

				System.out.println(content);

				Assert.assertTrue(content.contains("walkmod-imports-cleaner-plugin"));

				FileUtils.deleteDirectory(tmp);
			}

		}
	}

	@Test
	public void testTranformationPlugin() throws Exception {
		File tmp = new File("src/test/resources/initTestAddTrans");
		tmp.mkdirs();
		if (tmp.exists()) {
			String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
			System.setProperty("user.dir", tmp.getAbsolutePath());
			try {
				run(new String[] { "add", "imports-cleaner" });
			} catch (Exception e) {
				e.printStackTrace();
				Assert.assertFalse(false);
			} finally {
				System.setProperty("user.dir", userDir);

				File cfg = new File(tmp, "walkmod.xml");
				Assert.assertTrue(cfg.exists());

				String content = FileUtils.readFileToString(cfg);

				System.out.println(content);

				Assert.assertTrue(content.contains("imports-cleaner"));

				FileUtils.deleteDirectory(tmp);
			}

		}
	}

	@Test
	public void testAddProvider() throws Exception {
		File tmp = new File("src/test/resources/initTestAddProv");
		tmp.mkdirs();
		if (tmp.exists()) {
			String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
			System.setProperty("user.dir", tmp.getAbsolutePath());
			try {
				run(new String[] { "add-provider", "maven" });
			} catch (Exception e) {
				e.printStackTrace();
				Assert.assertFalse(false);
			} finally {
				System.setProperty("user.dir", userDir);

				File cfg = new File(tmp, "walkmod.xml");
				Assert.assertTrue(cfg.exists());

				String content = FileUtils.readFileToString(cfg);

				System.out.println(content);

				Assert.assertTrue(content.contains("maven"));

				FileUtils.deleteDirectory(tmp);
			}

		}
	}

	@Test
	public void testModule() throws Exception {
		File tmp = new File("src/test/resources/addModule");
		tmp.mkdirs();
		if (tmp.exists()) {
			String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
			System.setProperty("user.dir", tmp.getAbsolutePath());
			try {
				run(new String[] { "add-module", "module1" });
			} catch (Exception e) {
				e.printStackTrace();
				Assert.assertFalse(false);
			} finally {
				System.setProperty("user.dir", userDir);

				File cfg = new File(tmp, "walkmod.xml");
				Assert.assertTrue(cfg.exists());

				String content = FileUtils.readFileToString(cfg);

				System.out.println(content);

				Assert.assertTrue(content.contains("module1"));

				FileUtils.deleteDirectory(tmp);
			}

		}
	}

	@Test
	public void testRemoveTranf() throws Exception {
		File tmp = new File("src/test/resources/rmTransf");
		tmp.mkdirs();
		if (tmp.exists()) {
			String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
			System.setProperty("user.dir", tmp.getAbsolutePath());
			try {
				run(new String[] { "add", "imports-cleaner" });
				run(new String[] { "rm", "imports-cleaner" });
			} catch (Exception e) {
				e.printStackTrace();
				Assert.assertFalse(false);
			} finally {
				System.setProperty("user.dir", userDir);

				File cfg = new File(tmp, "walkmod.xml");
				Assert.assertTrue(cfg.exists());

				String content = FileUtils.readFileToString(cfg);

				FileUtils.deleteDirectory(tmp);

				System.out.println(content);

				Assert.assertTrue(!content.contains("imports-cleaner"));

			}

		}
	}

	@Test
	public void testSetWriter() throws Exception {
		File tmp = new File("src/test/resources/setWriter");
		tmp.mkdirs();
		if (tmp.exists()) {
			String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
			System.setProperty("user.dir", tmp.getAbsolutePath());
			try {
				run(new String[] { "add", "imports-cleaner" });
				run(new String[] { "set-writer", "javalang:string-writer" });

				File cfg = new File("walkmod.xml").getAbsoluteFile();
				Assert.assertTrue(cfg.exists());

				String content = FileUtils.readFileToString(cfg);

				System.out.println(content);

				cfg.delete();

				Assert.assertTrue(content.contains("javalang:string-writer"));
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				System.setProperty("user.dir", userDir);

			}

		}
	}

	@Test
	public void testChains() throws Exception {

		run(new String[] { "chains" });

	}

}
