package jgitCheck;

import java.util.Collection;

import org.eclipse.jgit.transport.PostReceiveHook;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceivePack;

public class HookCheck implements PostReceiveHook{

    public void onPostReceive(ReceivePack rcPck, Collection<ReceiveCommand> arg1) {
        
    }

}
