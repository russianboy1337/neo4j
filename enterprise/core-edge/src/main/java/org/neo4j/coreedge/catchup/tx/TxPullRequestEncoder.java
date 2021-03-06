/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.coreedge.catchup.tx;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import org.neo4j.coreedge.messaging.NetworkFlushableChannelNetty4;
import org.neo4j.coreedge.messaging.marshalling.storeid.StoreIdMarshal;

public class TxPullRequestEncoder extends MessageToMessageEncoder<TxPullRequest>
{
    @Override
    protected void encode( ChannelHandlerContext ctx, TxPullRequest request, List<Object> out ) throws Exception
    {
        ByteBuf encoded = ctx.alloc().buffer();
        encoded.writeLong( request.txId() );
        StoreIdMarshal.INSTANCE.marshal( request.expectedStoreId(), new NetworkFlushableChannelNetty4( encoded ) );
        out.add( encoded );
    }
}
