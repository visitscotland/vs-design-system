package com.visitscotland.brmx.translation;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jcr.*;
import javax.jcr.nodetype.NodeType;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class NodeBuilder {
    Map<String, Property> properties = new HashMap<>();
    String primaryNodeType;
    Map<String, List<Node>> childNodes = new HashMap<>();
    String nodeId;
    Session jcrSession;

    public NodeBuilder inJcrSession(Session jcrSession) {
        this.jcrSession = jcrSession;
        return this;
    }

    public NodeBuilder withNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public NodeBuilder withPrimaryNodeType(String primaryNodeType) {
        this.primaryNodeType = primaryNodeType;
        return this;
    }

    public NodeBuilder withProperty(String propertyPath, Value[] valueArray) throws Exception {
        Property mockProperty = mock(Property.class);
        lenient().when(mockProperty.getValues()).thenReturn(valueArray);
        properties.put(propertyPath, mockProperty);
        return this;
    }

    public NodeBuilder withProperty(String propertyPath, String propertyValue) throws Exception {
        Property mockProperty = mock(Property.class);
        lenient().when(mockProperty.getString()).thenReturn(propertyValue);
        properties.put(propertyPath, mockProperty);
        return this;
    }

    public NodeBuilder withPropertyThowsValueFormatException(String propertyPath) throws Exception {
        Property mockProperty = mock(Property.class);
        lenient().when(mockProperty.getString()).thenThrow(new ValueFormatException());
        properties.put(propertyPath, mockProperty);
        return this;
    }

    public NodeBuilder withChildNode(String propertyPath, Node childNode) {
        List<Node> nodes = childNodes.get(propertyPath);
        if (null == nodes) {
            nodes = new ArrayList<>();
            childNodes.put(propertyPath, nodes);
        }
        nodes.add(childNode);
        return this;
    }

    public NodeBuilder withEmptyChildNode(String propertyPath) {
        List<Node> nodes = childNodes.get(propertyPath);
        if (null == nodes) {
            nodes = new ArrayList<>();
            childNodes.put(propertyPath, nodes);
        }
        nodes.clear();
        return this;
    }

    public Node build() throws Exception {
        Node mockNode = mock(Node.class);
        lenient().when(mockNode.hasProperty(any())).thenReturn(false);

        if ( null != jcrSession && null != nodeId) {
            when(jcrSession.getNodeByIdentifier(eq(nodeId))).thenReturn(mockNode);
        }

        if ( null != primaryNodeType) {
            NodeType mockNodeType = mock(NodeType.class);
            when(mockNode.getPrimaryNodeType()).thenReturn(mockNodeType);
            when(mockNodeType.getName()).thenReturn(primaryNodeType);
        }

        for (Map.Entry<String, Property> propertyEntry : properties.entrySet()) {
            lenient().when(mockNode.hasProperty(eq(propertyEntry.getKey()))).thenReturn(true);
            when(mockNode.getProperty(eq(propertyEntry.getKey()))).thenReturn(propertyEntry.getValue());
        }

        for (Map.Entry<String, List<Node>> childNodeEntry : childNodes.entrySet()) {
            Iterator<Node> nodeIterator = childNodeEntry.getValue().iterator();

            final NodeIterator mockNodeIterator = mock(NodeIterator.class);
            when(mockNodeIterator.hasNext()).thenAnswer(new Answer<Boolean>() {
                @Override
                public Boolean answer(InvocationOnMock invocation) throws Throwable {
                    return nodeIterator.hasNext();
                }
            });
            lenient().when(mockNodeIterator.nextNode()).thenAnswer(new Answer<Node>() {
                @Override
                public Node answer(InvocationOnMock invocation) throws Throwable {
                    return nodeIterator.next();
                }
            });

            when(mockNode.getNodes(eq(childNodeEntry.getKey()))).thenReturn(mockNodeIterator);
        }

        return mockNode;
    }
}
