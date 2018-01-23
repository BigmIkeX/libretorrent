/*
 * Copyright (C) 2018 Yaroslav Pronin <proninyaroslav@mail.ru>
 *
 * This file is part of LibreTorrent.
 *
 * LibreTorrent is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LibreTorrent is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LibreTorrent.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.proninyaroslav.libretorrent.core.stateparcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/*
 * The class provides a package model with dynamically
 * changing information about state of the torrent.
 */

public class AdvanceStateParcel extends AbstractStateParcel<AdvanceStateParcel>
{
    public String torrentId = "";
    public int seeds = 0;
    public int peers = 0;
    public int downloadedPieces = 0;
    public long[] filesReceivedBytes = new long[0];
    public double shareRatio = 0.;
    public long activeTime = 0L;
    public long seedingTime = 0L;

    public AdvanceStateParcel()
    {
        super();
    }

    public AdvanceStateParcel(String torrentId, long[] filesReceivedBytes,
                              int seeds, int peers, int downloadedPieces,
                              double shareRatio, long activeTime, long seedingTime)
    {
        super(torrentId);

        this.torrentId = torrentId;
        this.filesReceivedBytes = filesReceivedBytes;
        this.seeds = seeds;
        this.peers = peers;
        this.downloadedPieces = downloadedPieces;
        this.shareRatio = shareRatio;
        this.activeTime = activeTime;
        this.seedingTime = seedingTime;
    }

    public AdvanceStateParcel(Parcel source)
    {
        super(source);

        torrentId = source.readString();
        filesReceivedBytes = source.createLongArray();
        seeds = source.readInt();
        peers = source.readInt();
        downloadedPieces = source.readInt();
        shareRatio = source.readDouble();
        activeTime = source.readLong();
        seedingTime = source.readLong();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        super.writeToParcel(dest, flags);

        dest.writeString(torrentId);
        dest.writeLongArray(filesReceivedBytes);
        dest.writeInt(seeds);
        dest.writeInt(peers);
        dest.writeInt(downloadedPieces);
        dest.writeDouble(shareRatio);
        dest.writeLong(activeTime);
        dest.writeLong(seedingTime);
    }

    public static final Parcelable.Creator<AdvanceStateParcel> CREATOR =
            new Parcelable.Creator<AdvanceStateParcel>()
            {
                @Override
                public AdvanceStateParcel createFromParcel(Parcel source)
                {
                    return new AdvanceStateParcel(source);
                }

                @Override
                public AdvanceStateParcel[] newArray(int size)
                {
                    return new AdvanceStateParcel[size];
                }
            };

    @Override
    public int compareTo(AdvanceStateParcel another)
    {
        return torrentId.compareTo(another.torrentId);
    }

    @Override
    public int hashCode()
    {
        int prime = 31, result = 1;

        result = prime * result + ((torrentId == null) ? 0 : torrentId.hashCode());
        result += Arrays.hashCode(filesReceivedBytes);
        result = prime * result + seeds;

        result = prime * result + peers;
        result = prime * result + downloadedPieces;
        long shareRationBits = Double.doubleToLongBits(shareRatio);
        result = prime * result + (int) (shareRationBits ^ (shareRationBits >>> 32));
        result = prime * result + (int) (activeTime ^ (activeTime >>> 32));
        result = prime * result + (int) (seedingTime ^ (seedingTime >>> 32));

        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof AdvanceStateParcel))
            return false;

        if (o == this)
            return true;

        AdvanceStateParcel state = (AdvanceStateParcel) o;

        return (torrentId == null || torrentId.equals(state.torrentId)) &&
                seeds == state.seeds &&
                peers == state.peers &&
                downloadedPieces == state.downloadedPieces &&
                Arrays.equals(filesReceivedBytes, state.filesReceivedBytes) &&
                shareRatio == state.shareRatio &&
                activeTime == state.activeTime &&
                seedingTime == state.seedingTime;
    }

    @Override
    public String toString()
    {
        return "AdvanceStateParcel{" +
                "torrentId='" + torrentId + '\'' +
                ", seeds=" + seeds +
                ", peers=" + peers +
                ", downloadedPieces=" + downloadedPieces +
                ", filesReceivedBytes=" + Arrays.toString(filesReceivedBytes) +
                ", activeTime=" + activeTime +
                ", seedingTime=" + seedingTime +
                ", shareRatio=" + shareRatio +
                '}';
    }
}