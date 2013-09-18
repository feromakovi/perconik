package sk.stuba.fiit.perconik.core.dom;

import java.util.Objects;
import javax.annotation.Nullable;
import org.eclipse.jdt.core.dom.ASTNode;
import sk.stuba.fiit.perconik.eclipse.jdt.core.dom.AstNodeType;
import sk.stuba.fiit.perconik.utilities.SmartStringBuilder;

public abstract class AstNodeDelta
{
	AstNodeDelta()
	{
	}
	
	// TODO move due to circular dependencies
//	static AstNodeDelta of(final ASTNode original, final ASTNode revised)
//	{
//		if (original != null && revised != null)
//		{
//			return AstNodeModification.of(original, revised);
//		}
//		else if (original != null)
//		{
//			return AstNodeDeletion.of(original);
//		}
//		else if (revised != null)
//		{
//			return AstNodeAddition.of(revised);
//		}
//		
//		throw new NullPointerException();
//	}
	
	@Override
	public final boolean equals(@Nullable Object o)
	{
		if (this == o)
		{
			return true;
		}

		if (!(o instanceof AstNodeDelta))
		{
			return false;
		}
		
		AstNodeDelta other = (AstNodeDelta) o;
		
		return Objects.equals(this.getOriginalNode(), other.getOriginalNode())
		    && Objects.equals(this.getRevisedNode(),  other.getRevisedNode());
	}
	
	@Override
	public final int hashCode()
	{
		return Objects.hashCode(this.getOriginalNode()) ^ Objects.hashCode(this.getRevisedNode());
	}

	@Override
	public final String toString()
	{
		return this.toString(0);
	}

	public abstract String toString(int indent);
	
	final SmartStringBuilder toStringBuilder(final int indent)
	{
		String type = this.getType().toString().toLowerCase();
		
		return new SmartStringBuilder().indent(indent).delta(2).append(type).appendln(':').tab();
	}
	
	public boolean hasOriginalNode()
	{
		return this.getOriginalNode() != null;
	}

	public abstract ASTNode getOriginalNode();
	
	public ASTNode getOriginalNodeRoot()
	{
		return AstNodes.root(this.getOriginalNode());
	}

	public ASTNode getOriginalNodeParent()
	{
		return AstNodes.parent(this.getOriginalNode());
	}

	public AstNodeType getOriginalNodeType()
	{
		return AstNodes.type(this.getOriginalNode());
	}

	public boolean hasRevisedNode()
	{
		return this.getRevisedNode() != null;
	}

	public abstract ASTNode getRevisedNode();
	
	public ASTNode getRevisedNodeRoot()
	{
		return AstNodes.root(this.getRevisedNode());
	}

	public ASTNode getRevisedNodeParent()
	{
		return AstNodes.parent(this.getRevisedNode());
	}

	public AstNodeType getRevisedNodeType()
	{
		return AstNodes.type(this.getRevisedNode());
	}
	
	public abstract AstNodeDeltaType getType();
}
