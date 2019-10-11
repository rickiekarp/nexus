package net.rickiekarp.botlib.plugin;

import java.security.*;

/**
 * Custom policy for the security-test.
 */
class PluginPolicy extends Policy {

	/**
	 * Returns {@link AllPermission} for any code sources that do not end in
	 * “/test.jar” and an empty set of permissions for code sources that do end
	 * in “/test.jar”, denying access to all local resources to the rogue
	 * plugin.
	 * 
	 * @param codeSource The code source to get the permissiosn for
	 * @return The permissions for the given code source
	 */
	public PermissionCollection getPermissions(CodeSource codeSource) {
		Permissions p = new Permissions();
		if (!codeSource.getLocation().toString().endsWith("/test.jar")) {
			p.add(new AllPermission());
		}
		return p;
	}

	/**
	 * Does nothing.
	 */
	public void refresh() {
	}

}
