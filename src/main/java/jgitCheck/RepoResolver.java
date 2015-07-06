package jgitCheck;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class RepoResolver<X> extends FileResolver<X> {
    /**
     * Open the repository and inject the repository name into the settings.
     */
    @Override
    public Repository open(final X req, final String name)
    throws RepositoryNotFoundException, ServiceNotEnabledException {
    Repository repo = super.open(req, name);

    System.out.println(name);
    return repo;
    }
}
